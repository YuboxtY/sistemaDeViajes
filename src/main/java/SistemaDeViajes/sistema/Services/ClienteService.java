package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Dominio.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> listaClientes();

    void guardar(Cliente cliente);

    void eliminar(Cliente cliente);

    Cliente encontrarCliente(Long id);

    // NUEVO: buscar un cliente por su cédula
    Optional<Cliente> buscarPorCedula(String cedula);
}

