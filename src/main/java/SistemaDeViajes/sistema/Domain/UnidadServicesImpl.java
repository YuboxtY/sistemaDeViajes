package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dao.UnidadDao;
import SistemaDeViajes.sistema.Services.UnidadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnidadServicesImpl implements UnidadServices {

    @Autowired
    private UnidadDao unidadDao;

    @Override
    @Transactional(readOnly = true)
    public List<Unidad> listarUnidades() {
        return (List<Unidad>) unidadDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Unidad> listarUnidadesDisponibles() {
        return unidadDao.findByEstado(Unidad.estadoUnidad.Disponible);
    }

    @Override
    @Transactional
    public void guardar(Unidad unidad) {
        if (unidad.getEstado() == null) {
            unidad.setEstado(Unidad.estadoUnidad.Disponible);
        }
        unidadDao.save(unidad);
    }

    @Override
    @Transactional
    public void eliminar(Unidad unidad) {
        unidadDao.delete(unidad);
    }


    @Transactional
    public void actualizarUnidad(Unidad unidad) {
        unidadDao.save(unidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Unidad encontrarUnidad(Unidad unidad) {
        return unidadDao.findById(unidad.getPlaca()).orElse(null);
    }


    @Transactional(readOnly = true)
    public Unidad encontrarPorPlaca(String placa) {
        return unidadDao.findById(placa).orElse(null);
    }
}
