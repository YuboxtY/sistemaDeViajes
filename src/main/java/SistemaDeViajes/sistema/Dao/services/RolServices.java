package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Rol;

import java.util.List;

public interface RolServices {
    List<Rol> listarRoles();
    Rol encontrarPorId(Long idRol);
}