package SistemaDeViajes.sistema.web;
import SistemaDeViajes.sistema.Dao.RutaDao;
import SistemaDeViajes.sistema.Dao.UnidadDao;
import SistemaDeViajes.sistema.Dao.services.TurnoService;
import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.RutaServices;

import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Domain.UnidadServices;
import SistemaDeViajes.sistema.Dominio.Turno;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private RutaServices rutaService;

    @Autowired
    private UnidadServices unidadService;

    @GetMapping("/nuevo")
    public String nuevoTurno(Model model) {
        model.addAttribute("turno", new Turno());
        model.addAttribute("rutas", rutaService.listarRutas());
        model.addAttribute("unidades", unidadService.listarUnidadesDisponibles()); // Solo disponibles
        return "turnos/formulario"; // Vista para crear turno
    }

    @PostMapping("/guardar")
    public String guardarTurno(@ModelAttribute("turno") Turno turno, RedirectAttributes redirectAttributes) {
        turnoService.guardar(turno);
        redirectAttributes.addFlashAttribute("success", "Turno creado correctamente.");
        return "redirect:/turnos/listar";
    }

    @GetMapping("/listar")
    public String listarTurnos(Model model) {
        var turnos = turnoService.listaTurno();
        model.addAttribute("turnos", turnos);
        return "turnos/listar"; // Vista con tabla o cards de turnos
    }
}
