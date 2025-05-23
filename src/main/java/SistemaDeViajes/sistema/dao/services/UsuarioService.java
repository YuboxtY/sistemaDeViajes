package SistemaDeViajes.sistema.dao.services;

import SistemaDeViajes.sistema.Dominio.Pago;
import SistemaDeViajes.sistema.Dominio.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UsuarioService {

    public List<Usuario> listaUsuarios();
    public void guardar (Usuario user );
    public void eliminar (Usuario user);
    public Usuario encontrarUsuario(Usuario user);
}