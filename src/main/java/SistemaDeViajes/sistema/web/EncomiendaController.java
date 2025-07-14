package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.ClienteDao;
import SistemaDeViajes.sistema.Dao.EncomiendaDao;
import SistemaDeViajes.sistema.Dao.UsuarioDao;
import SistemaDeViajes.sistema.Domain.Encomienda;
import SistemaDeViajes.sistema.Domain.EncomiendaServices;
import SistemaDeViajes.sistema.Dominio.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/encomiendas")
public class EncomiendaController {

    @Autowired
    private EncomiendaServices encomiendaServices;

    @Autowired
    private EncomiendaDao encomiendaDao;

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private UsuarioDao usuarioDao;

    // Listar
    @GetMapping("/list")
    public String inicio(Model modelo) {
        modelo.addAttribute("encomiendas", encomiendaServices.listarEncomiendas());
        return "encomienda/listar";
    }

    // Mostrar formulario de creación
    @GetMapping("/agregar")
    public String agregarEncomienda(Model modelo) {
        Encomienda encomienda = new Encomienda();
        encomienda.setRemitente(new Cliente());
        encomienda.setDestinatario(new Cliente());
        encomienda.setNumeroDeEnvio(generarNumeroDeEnvio());
        modelo.addAttribute("encomienda", encomienda);
        return "encomienda/agregar";
    }

    @PostMapping("/guardar")
    public String guardarEncomienda(@ModelAttribute("encomienda") Encomienda encomienda, Errors errores) {

        // Remitente
        Cliente remitente = encomienda.getRemitente();
        if (remitente != null && remitente.getCedula() != null) {
            Optional<Cliente> clienteExistente = clienteDao.findByCedula(remitente.getCedula());
            if (clienteExistente.isPresent()) {
                encomienda.setRemitente(clienteExistente.get());
            } else {
                usuarioDao.findByCedula(remitente.getCedula()).ifPresent(usuario -> {
                    remitente.setNombre(usuario.getNombre());
                    remitente.setApellido(usuario.getApellido());
                    remitente.setCorreo(usuario.getEmail());
                    clienteDao.save(remitente);
                });
            }
        }

        // Destinatario
        Cliente destinatario = encomienda.getDestinatario();
        if (destinatario != null && destinatario.getCedula() != null) {
            Optional<Cliente> clienteExistente = clienteDao.findByCedula(destinatario.getCedula());
            if (clienteExistente.isPresent()) {
                encomienda.setDestinatario(clienteExistente.get());
            } else {
                usuarioDao.findByCedula(destinatario.getCedula()).ifPresent(usuario -> {
                    destinatario.setNombre(usuario.getNombre());
                    destinatario.setApellido(usuario.getApellido());
                    destinatario.setCorreo(usuario.getEmail());
                    clienteDao.save(destinatario);
                });
            }
        }

        encomiendaServices.guardar(encomienda);
        return "redirect:/encomiendas/list";
    }


    // Mostrar formulario de edición
    @GetMapping("/editar/{idEncomienda}")
    public String editarEncomienda(@PathVariable Long idEncomienda, Model modelo) {
        Encomienda encomienda = encomiendaServices.encontrarEncomienda(idEncomienda);
        // Asegura que remitente y destinatario nunca sean null
        if (encomienda.getRemitente() == null) {
            encomienda.setRemitente(new Cliente());
        }
        if (encomienda.getDestinatario() == null) {
            encomienda.setDestinatario(new Cliente());
        }
        modelo.addAttribute("encomienda", encomienda);
        return "encomienda/agregar";
    }


    // Eliminar
    @GetMapping("/eliminar/{idEncomienda}")
    public String eliminarEncomienda(@PathVariable Long idEncomienda) {
        encomiendaServices.eliminar(idEncomienda);
        return "redirect:/encomiendas/list";
    }

    // Ver factura de la encomienda
    @GetMapping("/factura/{idEncomienda}")
    public String verFactura(@PathVariable Long idEncomienda, Model modelo) {
        Encomienda encomienda = encomiendaServices.encontrarEncomienda(idEncomienda);
        if (encomienda == null) {
            return "redirect:/encomiendas/list?error=NoEncontrada";
        }
        modelo.addAttribute("encomienda", encomienda);
        return "encomienda/factura";
    }


    private String generarNumeroDeEnvio() {
        return "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @GetMapping("/historial")
    public String verHistorialEncomiendas(Model model, Authentication auth) {
        String cedulaUsuario = auth.getName(); // Spring Security: devuelve la cédula como username
        List<Encomienda> historial = encomiendaServices.listarEncomiendasPorCedula(cedulaUsuario);
        model.addAttribute("encomiendas", historial);
        return "encomienda/historial";
    }
}
