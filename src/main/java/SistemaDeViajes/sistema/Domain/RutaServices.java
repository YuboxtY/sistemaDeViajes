package SistemaDeViajes.sistema.Domain;


import SistemaDeViajes.sistema.Dao.RutaDao;
import SistemaDeViajes.sistema.Dao.UnidadDao;
import SistemaDeViajes.sistema.Dominio.Turno;
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
        Ruta rutaCompleta = rutaDao.findById(ruta.getIdRuta())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        // Liberar las unidades asignadas a esta ruta a través de sus turnos
        if (rutaCompleta.getTurnos() != null) {
            for (Turno turno : rutaCompleta.getTurnos()) {
                Unidad unidad = turno.getUnidad();
                if (unidad != null && unidad.getEstado() == Unidad.estadoUnidad.Asignada) {
                    unidad.setEstado(Unidad.estadoUnidad.Disponible);
                    unidadDao.save(unidad);
                }
            }
        }

        // Eliminar la ruta junto con sus turnos
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



    @Transactional(readOnly = true)
    public Ruta encontrarRutaPorId(Long idRuta) {
        return rutaDao.findById(idRuta).orElse(null);
    }
}
