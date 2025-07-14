package SistemaDeViajes.sistema.Dominio;

import SistemaDeViajes.sistema.Dao.ClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ClienteService implements SistemaDeViajes.sistema.Services.ClienteService {
   @Autowired
   private ClienteDao clienteDao;

    @Override
    @Transactional (readOnly = true)
    public List<Cliente> listaClientes() {
        return (List<Cliente>) clienteDao.findAll(); // Devuelve una lista de todas las rutas
    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente encontrarCliente(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorCedula(String cedula) {
        return clienteDao.findByCedula(cedula);
    }
}
