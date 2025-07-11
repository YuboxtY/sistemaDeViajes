package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Turno;
import org.springframework.data.repository.CrudRepository;

public interface TurnoDao extends CrudRepository<Turno, Long> {
    // Aquí puedes agregar métodos específicos para Turno si es necesario
}
