package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteDao extends CrudRepository <Cliente, Long>{
    Optional<Cliente> findByCedula(String cedula);

}
