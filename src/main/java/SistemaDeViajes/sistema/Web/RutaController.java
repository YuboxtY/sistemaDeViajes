package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.RutaDao;
import SistemaDeViajes.sistema.Dao.UnidadDao;
import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.RutaServices;

import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Services.UnidadServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/rutas")
public class RutaController {

    // Inyección de dependencias para los servicios y DAOs
    @Autowired
    private RutaServices rutaServices;
    @Autowired
    private UnidadServices unidadServices;

    @Autowired
    private RutaDao rutaDao;
    @Autowired
    private UnidadDao unidadDao;

    @GetMapping("/list")
    public String inicio(Model modelo) {
        var rutas = rutaServices.listarRutas();
        log.info("Ejecutando el controlador de rutas");
        modelo.addAttribute("rutas", rutas); //
        modelo.addAttribute("unidadesDisponibles", unidadServices.listarUnidades()
                .stream()
                .filter(u -> u.getEstado() == Unidad.estadoUnidad.Disponible)
                .toList());
        return "rutas/listar";
    }

    // Crear nueva Ruta
    @GetMapping("/agregar")
    public String agregarRuta(Ruta ruta) { // Descomentar si tienes una clase Ruta
        return "rutas/agregar"; // Retorna la vista agregar.html
    }

    // Editar Ruta
    @GetMapping("/editar/{idRuta}") // Descomentar si tienes un ID para la ruta
    public String editarRuta(Ruta ruta, Model modelo) { // Descomentar si tienes una clase Ruta
        ruta = rutaServices.encontrarRuta(ruta); // Descomentar si tienes un método para encontrar rutas
        modelo.addAttribute("ruta", ruta); // Agrega la ruta al modelo para que esté disponible en la vista
        return "rutas/agregar"; // Redirige a la vista de agregar para editar
    }

    // Guardar Ruta
    @PostMapping("/guardar") // Descomentar si tienes un método para guardar rutas
    public String guardarRuta(Ruta ruta, Errors errores) { // Descomentar si tienes una clase Ruta
        rutaServices.guardar(ruta); // Descomentar
        return "redirect:/rutas/list"; // Redirige a la lista de rutas después de guardar
        }

    // Eliminar Ruta
    @GetMapping("/eliminar/{idRuta}") // Descomentar si tienes un ID para la ruta
    public String eliminarRuta(Ruta ruta) { // Descomentar si tienes una clase Ruta
        rutaServices.eliminar(ruta); // Descomentar si tienes un método para eliminar rutas
    return "redirect:/rutas/list"; // Redirige a la lista de rutas después de eliminar
    }






}
