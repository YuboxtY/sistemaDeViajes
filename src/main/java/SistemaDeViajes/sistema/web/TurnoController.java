package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.services.TurnoService;
import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.RutaServices;
import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Dominio.Turno;

import SistemaDeViajes.sistema.Services.UnidadServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@Slf4j
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private RutaServices rutaService;

    @Autowired
    private UnidadServices unidadService;

    // Vista con formulario de asignación de turno
    @GetMapping("/nuevo")
    public String nuevoTurno(Model model) {
        model.addAttribute("turno", new Turno());
        model.addAttribute("rutas", rutaService.listarRutas());
        model.addAttribute("unidades", unidadService.listarUnidadesDisponibles()); // Unidades disponibles
        return "turnos/formulario";
    }

    // Guardar nuevo turno desde formulario
    @PostMapping("/guardarTurno")
    public String guardarTurno(@RequestParam Long idRuta,
                               @RequestParam String placaUnidad,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
                               RedirectAttributes redirectAttributes) {
        try {
            Ruta ruta = rutaService.encontrarRutaPorId(idRuta);
            Unidad unidad = unidadService.encontrarPorPlaca(placaUnidad);
            if (unidad.getAsientos() == null || unidad.getAsientos().isEmpty()) {
                unidadService.guardar(unidad); // Esto generará los asientos
            }

            turnoService.asignarTurno(ruta, unidad, fecha, hora);

            redirectAttributes.addFlashAttribute("success", "Turno asignado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al asignar turno: " + e.getMessage());
        }

        return "redirect:/turnos/listar";
    }



    // Alternativa si envías datos manuales sin usar el objeto Turno
    @PostMapping("/asignar")
    public String asignarTurnoManual(@RequestParam Long idRuta,
                                     @RequestParam String placaUnidad,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
                                     RedirectAttributes redirectAttributes) {
        try {
            Ruta ruta = rutaService.encontrarRutaPorId(idRuta);
            Unidad unidad = unidadService.encontrarPorPlaca(placaUnidad);
            turnoService.asignarTurno(ruta, unidad, fecha, hora);
            redirectAttributes.addFlashAttribute("success", "Turno asignado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/turnos/listar";
    }

    // Mostrar todos los turnos
    @GetMapping("/listar")
    public String listarTurnos(Model model) {
        model.addAttribute("rutas", rutaService.listarRutas());
        model.addAttribute("unidadesDisponibles", unidadService.listarUnidadesDisponibles());
        return "turnos/listar"; // archivo HTML llamado listar.html dentro de /templates/turnos/
    }
    @GetMapping("/gestionar")
    public String gestionarTurnos(Model model) {
        model.addAttribute("rutas", rutaService.listarRutas());
        model.addAttribute("unidadesDisponibles", unidadService.listarUnidadesDisponibles());
        return "turnos/listar"; // el nombre del archivo Thymeleaf sin extensión
    }

}
