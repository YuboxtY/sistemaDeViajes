package SistemaDeViajes.sistema.Web;

import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Domain.UnidadServices;

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
@RequestMapping("/unidades")
public class UnidadController {

    @Autowired
    private UnidadServices unidadServices;

    @GetMapping("/")
    public String inicio(){
        return "index";
    }


    // Mapeamos la URL raíz
    @GetMapping("/list")
    public String inicio(Model model) { // Model es un objeto que se utiliza para pasar datos entre el controlador y la vista
        var unidades = unidadServices.listarUnidades();
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("unidades", unidades);
        return "unidad/listar"; // Retorna la vista listar.html
    }

    // Crear nueva Unidad
    @GetMapping("/agregar")
    public String agregarUnidad(Unidad unidad) {
        return "unidad/agregar";
    }

    // Editar Unidad
    @GetMapping("/editar/{placa}")
    public String editarUnidad(Unidad unidad, Model modelo) {
        unidad = unidadServices.encontrarUnidad(unidad); // Busca la unidad por su ID
        modelo.addAttribute("unidad", unidad); // Agrega la unidad al modelo para que esté disponible en la vista
        return "unidad/agregar"; // Redirige a la vista de agregar para editar
    }

    //Guardar Unidad
    @PostMapping("/guardar")
    public String guardarUnidad(Unidad unidad, Errors errores) {
        unidadServices.guardar(unidad); // Guarda la unidad
        return "redirect:/unidades/list";
// Redirige a la lista de unidades después de guardar

    }

    // Eliminar Unidad
    @GetMapping("/eliminar/{placa}")
    public String eliminarUnidad(Unidad unidad) {
        unidadServices.eliminar(unidad);
        return "redirect:/unidades/list";
// Redirige a la lista de unidades después de eliminar
    }

}
