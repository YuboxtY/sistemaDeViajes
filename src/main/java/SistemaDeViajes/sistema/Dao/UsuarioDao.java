package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao extends CrudRepository<Usuario, Long> , JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCedula(String cedula);
    List<Usuario> findByCedulaContainingIgnoreCaseOrNombreContainingIgnoreCase(String cedula, String nombre);
    Optional<Usuario> findById(Long id);
    Usuario findByEmail(String email);


}