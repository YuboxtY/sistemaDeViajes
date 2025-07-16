package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dao.BoletoDao;
import SistemaDeViajes.sistema.Dominio.Boleto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BoletoServiceImplemets implements BoletoService{

    @Autowired //inyeccion de depdendencia para instanciar la clase
    private BoletoDao boletoDao;

    @Override
    public List<Boleto> listaBoleto() {
        return (List<Boleto>) boletoDao.findAll();//encuentre de persona dao(capa de datos) todos los campos
    }

    @Override
    public void guardar(Boleto boleto) {
        boletoDao.save(boleto);
    }

    @Override
    public void eliminar(Boleto boleto) {
        boletoDao.delete(boleto);
    }

    @Override
    public List<Boleto> obtenerBoletosPorUsuarioId(Long idUsuario) {
        return boletoDao.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Boleto encontrarBoleto(Boleto boleto) {
        return boletoDao.findById(boleto.getIdBoleto()).orElse(null);
    }
}
