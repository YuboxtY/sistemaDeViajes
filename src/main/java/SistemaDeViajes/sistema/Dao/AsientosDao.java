package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Asiento;
import org.springframework.data.repository.CrudRepository;

public interface AsientoDao extends CrudRepository<Asiento, Long> {
    // Aquí puedes agregar métodos específicos para manejar asientos si es necesario
}
