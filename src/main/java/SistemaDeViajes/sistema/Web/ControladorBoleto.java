package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.AsientoDao;
import SistemaDeViajes.sistema.Dao.BoletoDao;
import SistemaDeViajes.sistema.Dao.services.AsientoServices;
import SistemaDeViajes.sistema.Dao.services.BoletoService;
import SistemaDeViajes.sistema.Dao.services.TurnoService;
import SistemaDeViajes.sistema.Dao.services.UsuarioService;
import SistemaDeViajes.sistema.Dominio.Asiento;
import SistemaDeViajes.sistema.Dominio.Boleto;
import SistemaDeViajes.sistema.Dominio.Turno;
import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.util.QRCodeUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/CompraBoleto")
public class ControladorBoleto {

    @Autowired private TurnoService turnoService;
    @Autowired private AsientoServices asientoServices;
    @Autowired private AsientoDao asientoDao;
    @Autowired private BoletoDao boletoRepository;
    @Autowired private UsuarioService usuarioService;
    @Autowired private BoletoService boletoService;

    @GetMapping("/listarTurnos")
    public String listarTurnos(Model model) {
        List<Turno> turnos = turnoService.listarTurnos();
        Map<Long, Long> asientosDisponiblesPorTurno = new HashMap<>();

        for (Turno turno : turnos) {
            long disponibles = asientoServices
                    .obtenerAsientosDisponibles(turno.getUnidad().getPlaca())
                    .stream()
                    .filter(a -> "disponible".equalsIgnoreCase(a.getEstado()))
                    .count();
            asientosDisponiblesPorTurno.put(turno.getId(), disponibles);
        }

        model.addAttribute("turnos", turnos);
        model.addAttribute("disponiblesPorTurno", asientosDisponiblesPorTurno);
        return "CompraBoleto/listarTurnos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioBoletos(@RequestParam Long turnoId, Model model) {
        Turno turno = turnoService.encontrarPorId(turnoId);
        List<Asiento> asientos = asientoDao.findByUnidadPlaca(turno.getUnidad().getPlaca());
        int filas = (int) Math.ceil(asientos.size() / 4.0);
        double precioUnitario = calcular_precios(turno.getRuta().getOrigen(), turno.getRuta().getDestino());

        model.addAttribute("turno", turno);
        model.addAttribute("asientos", asientos);
        model.addAttribute("filas", filas);
        model.addAttribute("precioUnitario", precioUnitario);

        return "CompraBoleto/SeleccionarAsiento";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam Long turnoId,
                           @RequestParam List<Long> asientosSeleccionados,
                           @AuthenticationPrincipal User userDetails,
                           Model model) {

        String cedula = userDetails.getUsername();
        Usuario cliente = usuarioService.findByCedula(cedula);
        Turno turno = turnoService.encontrarPorId(turnoId);
        List<Asiento> asientos = asientoDao.findAllById(asientosSeleccionados);

        double precioUnitario = calcular_precios(turno.getRuta().getOrigen(), turno.getRuta().getDestino());
        double subtotal = precioUnitario * asientos.size();
        double iva = subtotal * 0.15;
        double total = subtotal + iva;

        model.addAttribute("turno", turno);
        model.addAttribute("cliente", cliente);
        model.addAttribute("asientos", asientos);
        model.addAttribute("asientosIds", asientosSeleccionados);
        model.addAttribute("asientosNumeros", asientos.stream().map(Asiento::getNumero).collect(Collectors.joining(", ")));
        model.addAttribute("precioUnitario", precioUnitario);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("iva", iva);
        model.addAttribute("total", total);

        return "CompraBoleto/factura";
    }

    @Transactional
    @PostMapping("/realizarPago")
    public String realizarPago(@RequestParam Long turnoId,
                               @RequestParam String cedula,
                               @RequestParam("asientosIds") String asientosIdsRaw,
                               @RequestParam double precioUnitario,
                               @RequestParam double subtotal,
                               @RequestParam double iva,
                               @RequestParam double total,
                               @RequestParam String formaPago,
                               @RequestParam double montoIngresado,
                               Model model) {

        if (montoIngresado < total) {
            model.addAttribute("errorPago", "El monto ingresado es insuficiente. Total a pagar: $" + total);
            return "CompraBoleto/factura";
        }

        Usuario cliente = usuarioService.findByCedula(cedula);
        Turno turno = turnoService.encontrarPorId(turnoId);

        if (cliente == null || turno == null) {
            model.addAttribute("errorGeneral", "No se pudo encontrar al usuario o turno.");
            return "CompraBoleto/factura";
        }

        // Crear y guardar boleto
        Boleto boleto = new Boleto();
        boleto.setUsuario(cliente);
        boleto.setTurno(turno);
        boleto.setSubtotal(subtotal);
        boleto.setIva(iva);
        boleto.setTotal(total);
        boleto.setFormaPago(formaPago);
        boleto.setFechaCompra(LocalDate.now().toString());

        boleto = boletoRepository.save(boleto);

        // Procesar asientos
        List<Long> asientosIds = Arrays.stream(asientosIdsRaw.replaceAll("[\\[\\]\\s]", "").split(","))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Asiento> asientos = asientoDao.findAllById(asientosIds);
        for (Asiento asiento : asientos) {
            asiento.setEstado("no disponible");
            asiento.setBoleto(boleto);
        }
        asientoDao.saveAll(asientos);

        // Asignar los asientos al boleto (para la vista)
        boleto.setAsientos(asientos);

        // Generar QR
        try {
            Path directorioQR = Paths.get("src/main/resources/static/qrcodes");
            Files.createDirectories(directorioQR);

            String qrText = "Boleto #" + boleto.getIdBoleto() + ", Total: $" + boleto.getTotal();
            Path qrPath = directorioQR.resolve("boleto-" + boleto.getIdBoleto() + ".png");

            QRCodeUtil.generateQRCodeImage(qrText, 200, 200, qrPath.toString());

            model.addAttribute("qrImage", "/qrcodes/" + qrPath.getFileName());
        } catch (Exception e) {
            model.addAttribute("qrError", "No se pudo generar el código QR.");
        }

        model.addAttribute("boleto", boleto);
        model.addAttribute("cantidadAsientos", asientos.size());

        return "CompraBoleto/confirmacion";
    }

    private double calcular_precios(String inicio, String final_r) {
        if ((inicio.equals("Nabon") && final_r.equals("Cuenca")) || (inicio.equals("Cuenca") && final_r.equals("Nabon")))
            return 2.00;
        if ((inicio.equals("Cochapata") && final_r.equals("Cuenca")) || (inicio.equals("Cuenca") && final_r.equals("Cochapata")))
            return 3.00;
        if ((inicio.equals("Nabon") && final_r.equals("Cochapata")) || (inicio.equals("Cochapata") && final_r.equals("Nabon")))
            return 1.00;
        return 0.0;
    }
}
