package SistemaDeViajes.sistema.Web;

import SistemaDeViajes.sistema.Dao.EncomiendaDao;
import SistemaDeViajes.sistema.Domain.Encomienda;
import SistemaDeViajes.sistema.Domain.EncomiendaServices;
import SistemaDeViajes.sistema.Dominio.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/encomiendas")
public class EncomiendaController {

    @Autowired
    private EncomiendaServices encomiendaServices;

    @Autowired
    private EncomiendaDao encomiendaDao;

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
        // Inicializa remitente y destinatario para que Thymeleaf pueda enlazarlos
        encomienda.setRemitente(new Cliente());
        encomienda.setDestinatario(new Cliente());
        // Genera el número único
        encomienda.setNumeroDeEnvio(generarNumeroDeEnvio());
        modelo.addAttribute("encomienda", encomienda);
        return "encomienda/agregar";
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

    // Guardar (tanto nueva como editada)
    @PostMapping("/guardar")
    public String guardarEncomienda(
            @ModelAttribute("encomienda") Encomienda encomienda,
            Errors errores) {

        // Nota: con cascade PERSIST/MERGE en Encomienda, JPA creará
        // el remitente/destinatario nuevos si getId()==null.
        encomiendaServices.guardar(encomienda);
        return "redirect:/encomiendas/list";
    }

    // Eliminar
    @GetMapping("/eliminar/{idEncomienda}")
    public String eliminarEncomienda(@PathVariable Long idEncomienda) {
        encomiendaServices.eliminar(idEncomienda);
        return "redirect:/encomiendas/list";
    }

    private String generarNumeroDeEnvio() {
        return "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }



    @GetMapping("/factura/{idEncomienda}")
    public String verFactura(@PathVariable Long idEncomienda, Model modelo) {
        // Busca la encomienda por su ID
        Encomienda encomienda = encomiendaServices.encontrarEncomienda(idEncomienda);
        if (encomienda == null) {
            // Redirige con mensaje de error si no existe
            return "redirect:/encomiendas/list?error=NoEncontrada";
        }
        modelo.addAttribute("encomienda", encomienda);
        // Thymeleaf renderizará templates/encomienda/factura.html
        return "encomienda/factura";
    }



}

