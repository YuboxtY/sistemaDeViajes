package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Dominio.Boleto;

import java.util.List;

public interface BoletoService {

    public List<Boleto> listaBoletos();
    public void guardar (Boleto boleto);
    public void eliminar (Boleto cliente);
    public Boleto encontrarBoleto(Boleto boleto);
}
