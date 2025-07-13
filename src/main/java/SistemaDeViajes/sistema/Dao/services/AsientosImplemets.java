package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dao.AsientoDao;
import SistemaDeViajes.sistema.Dominio.Asiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientosImplemets implements AsientoServices {

    @Autowired
    private AsientoDao asientosDao;

    @Override
    public List<Asiento> listaBoleto() {
        return asientosDao.findAll();
    }

    @Override
    public void guardar(Asiento asiento) {
        asientosDao.save(asiento);
    }

    @Override
    public void eliminar(Asiento asiento) {
        asientosDao.delete(asiento);
    }

    @Override
    public Asiento encontrarAsientos(Asiento asiento) {
        return asientosDao.findById(asiento.getIdAsiento()).orElse(null);
    }

    @Override
    public List<Asiento> obtenerAsientosDisponibles(String placa) {
        return asientosDao.findByUnidadPlacaAndEstado(placa, "disponible");
    }
}
