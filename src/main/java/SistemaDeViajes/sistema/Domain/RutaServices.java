package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.dao.RutaDao;
import SistemaDeViajes.sistema.dao.UnidadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaServices implements SistemaDeViajes.sistema.Services.RutaServices {

    @Autowired // Inyección de dependencias
    private RutaDao rutaDao;

    @Autowired
    private UnidadDao unidadDao;

    @Override
    @Transactional
    public void guardar(Ruta ruta) {
        // Implementación del método para guardar una ruta
        rutaDao.save(ruta);
    }

    @Override
    @Transactional
    public void eliminar(Ruta ruta) {
        // obtener las unidades asignadas a esta ruta
        Ruta rutaCompleta = rutaDao.findById(ruta.getIdRuta())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        // actualizar estado de cada unidad asignada
        for (Unidad unidad : rutaCompleta.getUnidades()) {
            if (unidad.getEstado() == Unidad.estadoUnidad.Asignada) {
                unidad.setEstado(Unidad.estadoUnidad.Disponible);
                unidadDao.save(unidad);
            }
        }

        // eliminar la ruta
        rutaDao.delete(rutaCompleta);
    }

    @Override
    @Transactional(readOnly = true)
    public Ruta encontrarRuta(Ruta ruta) {
        // Implementación del método para encontrar una ruta por su ID
        return rutaDao.findById(ruta.getIdRuta()).orElse(null); // Devuelve la ruta si se encuentra, de lo contrario devuelve null
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ruta> listarRutas() {
        // Implementación del método para listar todas las rutas
        return (List<Ruta>) rutaDao.findAll(); // Devuelve una lista de todas las rutas
    }


    // Para asignar una unidad a una ruta

    @Transactional(readOnly = true)
    public List<Unidad> obtenerUnidadesDisponibles() {
        return unidadDao.findByEstado(Unidad.estadoUnidad.Disponible);
    }

    @Transactional
    public void asignarUnidadARuta(Long idRuta, String placaUnidad) {
        Ruta ruta = rutaDao.findById(idRuta)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        Unidad unidad = unidadDao.findById(placaUnidad)
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));

        if (!ruta.getUnidades().contains(unidad)) {
            ruta.getUnidades().add(unidad);
            unidad.getRutas().add(ruta);

            if (unidad.getEstado() == Unidad.estadoUnidad.Disponible) {
                unidad.setEstado(Unidad.estadoUnidad.Asignada);
            }

            rutaDao.save(ruta);
            unidadDao.save(unidad);
        }
    }

    @Transactional(readOnly = true)
    public Ruta encontrarRutaPorId(Long idRuta) {
        return rutaDao.findById(idRuta).orElse(null);
    }
}
