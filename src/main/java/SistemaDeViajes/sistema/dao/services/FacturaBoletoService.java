package SistemaDeViajes.sistema.dao.services;

import SistemaDeViajes.sistema.Dominio.Factura_Boleto;

import java.util.List;


public interface FacturaBoletoService {

    public List<Factura_Boleto> listaFBoletos();
    public void guardar (Factura_Boleto FBoleto );
    public void eliminar (Factura_Boleto FBoleto );
    public Factura_Boleto encontrarFBoleto(Factura_Boleto FBoleto );
}