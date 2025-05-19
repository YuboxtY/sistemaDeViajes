package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dao.RutaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaServices implements SistemaDeViajes.sistema.Services.RutaServices {

    @Autowired // Inyección de dependencias
    private RutaDao rutaDao;


    @Override
    @Transactional
    public void guardar(Ruta ruta) {
        // Implementación del método para guardar una ruta
        rutaDao.save(ruta);
    }

    @Override
    @Transactional
    public void eliminar(Ruta ruta) {
        // Implementación del método para eliminar una ruta
        rutaDao.delete(ruta);
    }

    @Override
    @Transactional (readOnly = true)
    public Ruta encontrarRuta(Ruta ruta) {
        // Implementación del método para encontrar una ruta por su ID
        return rutaDao.findById(ruta.getIdRuta()).orElse(null); // Devuelve la ruta si se encuentra, de lo contrario devuelve null
    }

    @Override
    @Transactional (readOnly = true)
    public List<Ruta> listarRutas() {
        // Implementación del método para listar todas las rutas
        return (List<Ruta>) rutaDao.findAll(); // Devuelve una lista de todas las rutas
    }
}
