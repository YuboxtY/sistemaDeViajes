package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.Unidad;
import SistemaDeViajes.sistema.Dominio.Turno;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TurnoDao extends CrudRepository<Turno, Long> {
    // Aquí puedes agregar métodos específicos para Turno si es necesario
    List<Turno> findByRuta(Ruta ruta);
    List<Turno> findByUnidad(Unidad unidad);
}
