package SistemaDeViajes.sistema.Web;

import SistemaDeViajes.sistema.Dao.EncomiendaDao;
import SistemaDeViajes.sistema.Domain.Encomienda;
import SistemaDeViajes.sistema.Domain.EncomiendaServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/encomiendas")
public class EncomiendaController {
    @Autowired
    private EncomiendaDao encomiendaDao;

    @Autowired
    private EncomiendaServices encomiendaServices;

    @GetMapping("/list")
    public String inicio(Model modelo) {
        var encomiendas = encomiendaServices.listarEncomiendas(); // Obtiene la lista de encomiendas
        modelo.addAttribute("encomiendas", encomiendas); // Agrega la lista al modelo
        return "encomienda/listar"; // Retorna la vista listar.html
    }

    // Crear nueva Encomienda
    @GetMapping("/agregar")
    public String agregarEncomienda(Encomienda encomienda) {
        return "encomienda/agregar"; // Retorna la vista agregar.html
    }

    // Editar Encomienda
    @GetMapping("/editar/{idEncomienda}")
    public String editarEncomienda(Encomienda encomienda, Model modelo) {
        encomienda = encomiendaServices.encontrarEncomienda(encomienda); // Busca la encomienda por su ID
        modelo.addAttribute("encomienda", encomienda); // Agrega la encomienda al modelo para que esté disponible en la vista
        return "encomienda/agregar"; // Redirige a la vista de agregar para editar
    }

    // Guardar Encomienda
    @PostMapping("/guardar")
    public String guardarEncomienda(Encomienda encomienda, Errors errores) {
        encomiendaServices.guardar(encomienda); // Guarda la encomienda
        return "redirect:/encomiendas/list"; // Redirige a la lista de encomiendas después de guardar
    }

    // Eliminar Encomienda
    @GetMapping("/eliminar/{idEncomienda}")
    public String eliminarEncomienda(Encomienda encomienda) {
        encomiendaServices.eliminar(encomienda); // Elimina la encomienda
        return "redirect:/encomiendas/list"; // Redirige a la lista de encomiendas después de eliminar
    }

}
