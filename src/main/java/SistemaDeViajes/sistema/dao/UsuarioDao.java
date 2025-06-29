package SistemaDeViajes.sistema.dao;

import SistemaDeViajes.sistema.Dominio.Boleto;
import SistemaDeViajes.sistema.Dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> , JpaRepository<Usuario, Long> {
    Usuario findByCedula(String cedula);
}