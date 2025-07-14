package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioDao extends CrudRepository<Usuario, Long> , JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCedula(String cedula);

}