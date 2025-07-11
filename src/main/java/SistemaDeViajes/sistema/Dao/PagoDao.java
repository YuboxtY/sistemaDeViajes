package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Pago;
import org.springframework.data.repository.CrudRepository;

public interface PagoDao extends CrudRepository<Pago, Long> {
}