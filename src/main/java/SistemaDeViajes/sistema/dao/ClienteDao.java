package SistemaDeViajes.sistema.dao;

import SistemaDeViajes.sistema.Dominio.Boleto;
import SistemaDeViajes.sistema.Dominio.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Long> {
}