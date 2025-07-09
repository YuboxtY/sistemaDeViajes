package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dao.EncomiendaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EncomiendaServices implements SistemaDeViajes.sistema.Services.EncomiendaServices {

    @Autowired
    private EncomiendaDao encomiendaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Encomienda> listarEncomiendas() {
        return (List<Encomienda>) encomiendaDao.findAll(); // Devuelve una lista de todas las encomiendas
    }

    @Override
    @Transactional
    public void guardar(Encomienda encomienda) {
        encomiendaDao.save(encomienda);

    }

    @Override
    @Transactional
    public void eliminar(Encomienda encomienda) {
        encomiendaDao.delete(encomienda);

    }

    @Override
    @Transactional(readOnly = true)
    public Encomienda encontrarEncomienda(Encomienda encomienda) {
        return encomiendaDao.findById(encomienda.getIdEncomienda()).orElse(null); // Devuelve la encomienda si se encuentra, de lo contrario devuelve null
    }
}
