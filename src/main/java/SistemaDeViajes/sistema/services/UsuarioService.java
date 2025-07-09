package SistemaDeViajes.sistema.services;

import SistemaDeViajes.sistema.Dominio.Usuario;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> listaUsuario();
    public void guardar (Usuario user);
    public void eliminar (Usuario user);
    public Usuario encontrarUser(Usuario user);
}
