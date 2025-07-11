package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dao.BoletoDao;
import SistemaDeViajes.sistema.Dao.TurnoDao;
import SistemaDeViajes.sistema.Dominio.Boleto;
import SistemaDeViajes.sistema.Dominio.Turno;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TurnoImplemets implements TurnoService {

    @Autowired //inyeccion de depdendencia para instanciar la clase
    private TurnoDao turnoDao;
    @Override
    public List<Turno> listaBoleto() {
        return (List<Turno>) turnoDao.findAll();//encuentre de persona dao(capa de datos) todos los campos
    }

    @Override
    public void guardar(Turno turno) {
        turnoDao.save(turno);
    }

    @Override
    public void eliminar(Turno turno) {
        turnoDao.delete(turno);
    }

    @Override
    public Turno encontrarTurno(Turno turno) {
        return turnoDao.findById(turno.getId()).orElse(null);
    }
}
