package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Usuario;

import java.util.List;
import java.util.Optional;


public interface UsuarioService {

    public List<Usuario> listaUsuarios();
    public void guardar (Usuario user );
    public void eliminar (Usuario user);
    public Usuario encontrarUsuario(Usuario user);
    public String encriptarPassword(String pasword);
    public Optional<Usuario> findByCedula(String cedula);

}