package SistemaDeViajes.sistema.Services;


import SistemaDeViajes.sistema.Dominio.Factura_Boleto;

import java.util.List;

public interface FacturaBoletoService
{
    public List<Factura_Boleto> listafaFacturaBoletos();
    public void guardar (Factura_Boleto FBoleto);
    public void eliminar (Factura_Boleto FBoleto);
    public Factura_Boleto encontrarFacturaBoleto(Factura_Boleto FBoleto);
}
