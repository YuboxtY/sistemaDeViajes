package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Cliente;

import java.util.List;


public interface ClienteService {

    public List<Cliente> listaClientes();
    public void guardar (Cliente cliente );
    public void eliminar (Cliente cliente );
    public Cliente encontrarCliente(Cliente cliente );
}