package SistemaDeViajes.sistema.dao;

import SistemaDeViajes.sistema.Dominio.Boleto;
import org.springframework.data.repository.CrudRepository;

public interface BoletoDao extends CrudRepository<Boleto, Long> {
}
