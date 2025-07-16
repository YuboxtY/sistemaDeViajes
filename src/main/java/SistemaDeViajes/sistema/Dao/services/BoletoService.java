package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Boleto;

import java.util.List;

public interface BoletoService {

    public List<Boleto> listaBoleto();
    public void guardar (Boleto boleto);
    public void eliminar (Boleto boleto);
    public Boleto encontrarBoleto(Boleto boleto);
    List<Boleto> obtenerBoletosPorUsuarioId(Long idUsuario);
}