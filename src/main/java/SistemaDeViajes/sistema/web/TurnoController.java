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

    // 👉 Mostrar formulario para crear un nuevo turno
    @GetMapping("/nuevo")
    public String nuevoTurno(Model model) {
        model.addAttribute("turno", new Turno());
        model.addAttribute("rutas", rutaService.listarRutas());
        model.addAttribute("unidades", unidadService.listarUnidadesDisponibles());
        return "turnos/listar"; // ⚠️ Asegúrate de tener esta vista (crearTurno.html)
    }

    // 👉 Guardar nuevo turno desde el formulario
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
                unidadService.guardar(unidad); // Genera asientos si no existen
            }

            turnoService.asignarTurno(ruta, unidad, fecha, hora);
            redirectAttributes.addFlashAttribute("success", "Turno asignado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al asignar turno: " + e.getMessage());
        }
        return "redirect:/turnos/gestionar";
    }

    // 👉 Guardar edición de turno existente
    @PostMapping("/guardar")
    public String actualizarTurno(@RequestParam Long id,
                                  @RequestParam Long rutaId,
                                  @RequestParam String placa,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
                                  RedirectAttributes redirectAttrs) {
        try {
            Turno turno = turnoService.encontrarPorId(id);
            if (turno == null) throw new RuntimeException("Turno no encontrado");

            turno.setFecha(fecha);
            turno.setHora(hora);
            turno.setRuta(rutaService.encontrarRutaPorId(rutaId));
            turno.setUnidad(unidadService.encontrarPorPlaca(placa));

            turnoService.guardar(turno);
            redirectAttrs.addFlashAttribute("success", "Turno actualizado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al guardar turno: " + e.getMessage());
        }

        return "redirect:/turnos/gestionar";
    }

    // 👉 Eliminar turno
    @PostMapping("/eliminar")
    public String eliminarTurno(@RequestParam Long id, RedirectAttributes redirectAttrs) {
        try {
            Turno turno = turnoService.encontrarPorId(id);
            if (turno == null) {
                throw new RuntimeException("Turno no encontrado");
            }

            Unidad unidad = turno.getUnidad();
            if (unidad != null) {
                unidad.setEstado(Unidad.estadoUnidad.Disponible); // Cambia el estado
                unidadService.guardar(unidad);                    // Persiste el nuevo estado
            }

            turnoService.eliminar(id); // Elimina el turno
            redirectAttrs.addFlashAttribute("success", "Turno eliminado correctamente y unidad disponible.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al eliminar turno: " + e.getMessage());
        }
        return "redirect:/turnos/gestionar";
    }


    // 👉 Vista de gestión de turnos (listado)
    @GetMapping("/gestionar")
    public String gestionarTurnos(Model model) {
        model.addAttribute("turnos", turnoService.listarTurnos());
        model.addAttribute("rutas", rutaService.listarRutas());
        model.addAttribute("unidades", unidadService.listarUnidades());
        return "turnos/GestionTurnos";
    }
}
