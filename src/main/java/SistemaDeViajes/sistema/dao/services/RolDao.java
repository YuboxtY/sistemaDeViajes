package SistemaDeViajes.sistema.dao.services;

import SistemaDeViajes.sistema.Dominio.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolDao extends JpaRepository<Rol, Long> {
    Rol findByIdRol(Long ID); // Busca el rol por su nombre
}
