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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/rutas")
public class RutaController {

    // Inyección de dependencias para los servicios y DAOs
    @Autowired
    private RutaServices rutaServices;
    @Autowired
    private SistemaDeViajes.sistema.Services.UnidadServices unidadServices;

    @Autowired
    private RutaDao rutaDao;

//    @Autowired
//    private UnidadDao unidadDao;

    @GetMapping("/list")
    public String inicio(Model modelo) {
        var rutas = rutaServices.listarRutas();
        log.info("Ejecutando el controlador de rutas");
        modelo.addAttribute("rutas", rutas); //
//        modelo.addAttribute("unidadesDisponibles", unidadServices.listarUnidades()
//                .stream()
//                .filter(u -> u.getEstado() == Unidad.estadoUnidad.Disponible)
//                .toList());
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



//    // Asignar unidad a ruta
//    @PostMapping("/asignar-unidad")
//    public String asignarUnidad(
//            @RequestParam Long idRuta,
//            @RequestParam String placaUnidad,
//            RedirectAttributes redirectAttributes) {
//
////        try {
////            rutaServices.asignarUnidadARuta(idRuta, placaUnidad);
////            redirectAttributes.addFlashAttribute("success", "Unidad asignada correctamente");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", "Error al asignar unidad: " + e.getMessage());
////        }
//
//        return "redirect:/rutas/list";
//    }

//
//    @PostMapping("/liberar-unidad/{idRuta}")
//    public String liberarUnidad(
//            @PathVariable Long idRuta,
//            @RequestParam String placaUnidad,
//            RedirectAttributes redirectAttributes) {
//
//        try {
//            // Obtener la ruta con sus unidades
//            Ruta ruta = rutaDao.findById(idRuta)
//                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
//
//            // Buscar la unidad específica
//            Unidad unidad = ruta.getUnidades().stream()
//                    .filter(u -> u.getPlaca().equals(placaUnidad))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Unidad no encontrada en esta ruta"));
//
//            // Liberar la unidad (eliminar relación bidireccional)
//            ruta.getUnidades().remove(unidad);
//            unidad.getRutas().remove(ruta);
//
//            // Actualizar estado de la unidad si estaba asignada
//            if (unidad.getEstado() == Unidad.estadoUnidad.Asignada) {
//                unidad.setEstado(Unidad.estadoUnidad.Disponible);
//            }
//
//            // Guardar cambios (JPA actualiza automáticamente la tabla intermedia)
//            rutaDao.save(ruta);
//            unidadDao.save(unidad);
//
//            redirectAttributes.addFlashAttribute("success", "Unidad liberada correctamente");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error al liberar unidad: " + e.getMessage());
//        }
//
//        return "redirect:/rutas/list";
//    }

}
