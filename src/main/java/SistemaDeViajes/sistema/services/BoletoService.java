package SistemaDeViajes.sistema.services;

import SistemaDeViajes.sistema.Dominio.Boleto;

import java.util.List;


import java.util.List;

public interface BoletoService {

    public List<Boleto> listaBoleto();
    public void guardar (Boleto boleto);
    public void eliminar (Boleto boleto);
    public Boleto encontrarBoleto(Boleto boleto);
}