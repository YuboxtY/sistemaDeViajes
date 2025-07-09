package SistemaDeViajes.sistema.services;
import SistemaDeViajes.sistema.Dominio.Cliente;


import java.util.List;

public interface PersonaService {
    public List<Cliente> listaCliente();
    public void guardar (Cliente cliente);
    public void eliminar (Cliente cliente);
    public Cliente encontrarUser(Cliente cliente);
}
