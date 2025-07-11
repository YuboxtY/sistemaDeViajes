package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.services.AsientoServices;
import SistemaDeViajes.sistema.Dao.services.TurnoService;
import SistemaDeViajes.sistema.Dominio.Turno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Asegúrate de importar Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/CompraBoleto")
public class ControladorBoleto {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private AsientoServices asientoServices;

    @GetMapping("/listarTurnos")
    public String listarTurnos(Model model) {
        List<Turno> turnos = turnoService.listarTurnos(); // o listaTurno(), según tu método

        // Mapa para guardar la cantidad de asientos disponibles por cada turno
        Map<Long, Long> asientosDisponiblesPorTurno = new HashMap<>();

        for (Turno turno : turnos) {
            String placa = turno.getUnidad().getPlaca();

            // Llamamos al service para obtener los asientos disponibles de la unidad del turno
            long disponibles = asientoServices.obtenerAsientosDisponibles(placa).size();

            asientosDisponiblesPorTurno.put(turno.getId(), disponibles);
        }

        model.addAttribute("turnos", turnos);
        model.addAttribute("disponiblesPorTurno", asientosDisponiblesPorTurno);

        return "CompraBoleto/listarTurnos";
    }
}
