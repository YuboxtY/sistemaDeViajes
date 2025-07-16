package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Dominio.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TurnoService {

    void asignarTurno(Ruta ruta, Unidad unidad, LocalDate fecha, LocalTime hora);
    public void eliminar(Long id);
    List<Turno> listarTurnos();
    public void guardar(Turno turno);
    List<Turno> obtenerTurnosPorRuta(Ruta ruta);

    List<Turno> obtenerTurnosPorUnidad(Unidad unidad);

    Turno encontrarPorId(Long id);

}
