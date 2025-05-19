package SistemaDeViajes.sistema.dao;


import SistemaDeViajes.sistema.Dominio.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository <Usuario, Long> {
}
