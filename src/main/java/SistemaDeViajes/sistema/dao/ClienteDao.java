package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository <Cliente, Long>{
}
