package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.AsientoDao;
import SistemaDeViajes.sistema.Dao.services.AsientoServices;
import SistemaDeViajes.sistema.Dao.services.TurnoService;
import SistemaDeViajes.sistema.Dao.services.UsuarioService;
import SistemaDeViajes.sistema.Dominio.Asiento;
import SistemaDeViajes.sistema.Dominio.Boleto;
import SistemaDeViajes.sistema.Dominio.Turno;
import SistemaDeViajes.sistema.Dominio.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/CompraBoleto")
public class ControladorBoleto {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private AsientoServices asientoServices;

    @Autowired
    private AsientoDao asientoDao;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listarTurnos")
    public String listarTurnos(Model model) {
        List<Turno> turnos = turnoService.listarTurnos();
        Map<Long, Long> asientosDisponiblesPorTurno = new HashMap<>();

        for (Turno turno : turnos) {
            String placa = turno.getUnidad().getPlaca();

            long disponibles = asientoServices
                    .obtenerAsientosDisponibles(placa)
                    .stream()
                    .filter(a -> a.getEstado() != null && a.getEstado().equalsIgnoreCase("disponible"))
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

        // 🔍 Obtener todos los asientos de la unidad (no solo los disponibles)
        List<Asiento> asientos = asientoDao.findByUnidadPlaca(turno.getUnidad().getPlaca());

        // ✅ Calcular cuántas filas mostrar en el layout (4 asientos por fila)
        int filas = (int) Math.ceil(asientos.size() / 4.0);
        model.addAttribute("filas", filas);


        // 💰 Calcular precio unitario según origen/destino
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
                           @RequestParam String cedula,
                           @RequestParam String formaPago,
                           Model model) {

        Turno turno = turnoService.encontrarPorId(turnoId);
        Usuario cliente = usuarioService.findByCedula(cedula);

        if (cliente == null) {
            model.addAttribute("error", "No se encontró el usuario con cédula: " + cedula);
            return "redirect:/login";
        }

        List<Asiento> asientos = asientoDao.findAllById(asientosSeleccionados);
        String numerosAsientos = asientos.stream()
                .map(Asiento::getNumero)
                .collect(Collectors.joining(", "));

        double precioUnitario = calcular_precios(turno.getRuta().getOrigen(), turno.getRuta().getDestino());
        double subtotal = precioUnitario * asientos.size();
        double iva = subtotal * 0.15;
        double total = subtotal + iva;

        Boleto boleto = new Boleto();
        boleto.setUsuario(cliente);
        boleto.setTurno(turno);
        boleto.setSubtotal(subtotal);
        boleto.setIva(iva);
        boleto.setTotal(total);
        boleto.setFormaPago(formaPago);
        boleto.setFechaCompra(LocalDate.now().toString());

        for (Asiento asiento : asientos) {
            asiento.setEstado("reservado");
            asiento.setBoleto(boleto);
        }

        boleto.setAsientos(asientos);
        asientoDao.saveAll(asientos);

        model.addAttribute("boleto", boleto);
        return "CompraBoleto/factura";
    }

    private double calcular_precios(String inicio, String final_r) {
        double precios = 0;
        if ((inicio.equals("Nabon") && final_r.equals("Cuenca")) || (inicio.equals("Cuenca") && final_r.equals("Nabon"))) {
            precios = 2.00;
        } else if ((inicio.equals("Cochapata") && final_r.equals("Cuenca")) || (inicio.equals("Cuenca") && final_r.equals("Cochapata"))) {
            precios = 3.00;
        } else if ((inicio.equals("Nabon") && final_r.equals("Cochapata")) || (inicio.equals("Cochapata") && final_r.equals("Nabon"))) {
            precios = 1.00;
        }
        return precios;
    }
}
