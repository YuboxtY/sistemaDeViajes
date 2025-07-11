package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Asiento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AsientoDao extends CrudRepository<Asiento, Long> {
    List<Asiento> findByUnidadPlacaAndDisponibleTrue(String placa);
// Aquí puedes agregar métodos específicos para manejar asientos si es necesario
}
