package SistemaDeViajes.sistema.services;

import SistemaDeViajes.sistema.Dominio.Boleto;

import java.util.List;

public interface BoletoService {

    public List<Boleto> listaBoletos();
    public void guardar (Boleto boleto);
    public void eliminar (Boleto cliente);
    public Boleto encontrarBoleto(Boleto boleto);
}
