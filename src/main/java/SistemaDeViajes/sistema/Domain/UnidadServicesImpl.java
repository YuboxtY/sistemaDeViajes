package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dao.AsientoDao;
import SistemaDeViajes.sistema.Dao.UnidadDao;
import SistemaDeViajes.sistema.Dominio.Asiento;
import SistemaDeViajes.sistema.Services.UnidadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnidadServicesImpl implements UnidadServices {

    @Autowired
    private UnidadDao unidadDao;
    @Autowired
    private AsientoDao asientoDao;

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
    public void eliminar(Unidad unidad) {
        Unidad existente = unidadDao.findById(unidad.getPlaca()).orElse(null);
        if (existente != null) {
            unidadDao.delete(existente);
        }
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


    @Override
    @Transactional
    public void guardar(Unidad unidad) {
        if (unidad.getEstado() == null) {
            unidad.setEstado(Unidad.estadoUnidad.Disponible);
        }
        unidadDao.save(unidad); // Guardar primero la unidad

        int capacidad = unidad.getCapacidad();
        int numeroAsiento = 1;

        while (numeroAsiento <= capacidad) {
            // Izquierda
            if (numeroAsiento <= capacidad) {
                Asiento a1 = new Asiento();
                a1.setNumero(numeroAsiento + "V");
                a1.setEstado("disponible");
                a1.setUnidad(unidad);
                asientoDao.save(a1);
                numeroAsiento++;
            }

            if (numeroAsiento <= capacidad) {
                Asiento a2 = new Asiento();
                a2.setNumero(numeroAsiento + "P");
                a2.setEstado("disponible");
                a2.setUnidad(unidad);
                asientoDao.save(a2);
                numeroAsiento++;
            }

            // Derecha
            if (numeroAsiento <= capacidad) {
                Asiento a3 = new Asiento();
                a3.setNumero(numeroAsiento + "P");
                a3.setEstado("disponible");
                a3.setUnidad(unidad);
                asientoDao.save(a3);
                numeroAsiento++;
            }

            if (numeroAsiento <= capacidad) {
                Asiento a4 = new Asiento();
                a4.setNumero(numeroAsiento + "V");
                a4.setEstado("disponible");
                a4.setUnidad(unidad);
                asientoDao.save(a4);
                numeroAsiento++;
            }
        }
    }


}
