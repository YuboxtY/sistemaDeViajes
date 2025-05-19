package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dao.UnidadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnidadServices implements SistemaDeViajes.sistema.Services.UnidadServices {

    @Autowired // Inyección de dependencias
    private UnidadDao unidadDao;

    @Override
    @Transactional (readOnly = true) // Indica que este método es de solo lectura
    public List<Unidad> listarUnidades() {
        // Implementación del método para listar todas las unidades
        return (List<Unidad>) unidadDao.findAll(); // Devuelve una lista de todas las unidades

    }

    @Override
    @Transactional // Indica que este método debe ser ejecut
    public void guardar(Unidad unidad) {
        // Implementación del método para guardar una unidad
        unidadDao.save(unidad);
    }

    @Override
    @Transactional
    public void eliminar(Unidad unidad) {
        // Implementación del método para eliminar una unidad
        unidadDao.delete(unidad);
    }

    @Override
    @Transactional (readOnly = true)
    public Unidad encontrarUnidad(Unidad unidad) {
        // Implementación del método para encontrar una unidad por su ID
       return unidadDao.findById(unidad.getPlaca()).orElse(null);
    }
}
