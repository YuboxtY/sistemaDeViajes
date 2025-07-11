package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dao.TurnoDao;
import SistemaDeViajes.sistema.Dao.UnidadDao;
import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Dominio.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TurnoServiceImpl implements TurnoService {

    @Autowired
    private TurnoDao turnoDao;
    @Autowired
    private UnidadDao unidadDao;

    @Override
    @Transactional
    public void asignarTurno(Ruta ruta, Unidad unidad, LocalDate fecha, LocalTime hora) {
        // Validación opcional
        if (unidad.getEstado() == Unidad.estadoUnidad.Asignada) {
            throw new RuntimeException("La unidad ya está asignada a otro turno.");
        }

        Turno turno = new Turno();
        turno.setRuta(ruta);
        turno.setUnidad(unidad);
        turno.setFecha(fecha);
        turno.setHora(hora);

        // Cambiar estado de unidad
        unidad.setEstado(Unidad.estadoUnidad.Asignada);

        turnoDao.save(turno);      // guarda el turno
        unidadDao.save(unidad);    // persiste el nuevo estado de la unidad
    }

    @Override
    @Transactional(readOnly = true)
    public List<Turno> listarTurnos() {
        return (List<Turno>) turnoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Turno> obtenerTurnosPorRuta(Ruta ruta) {
        return turnoDao.findByRuta(ruta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Turno> obtenerTurnosPorUnidad(Unidad unidad) {
        return turnoDao.findByUnidad(unidad);
    }
}
