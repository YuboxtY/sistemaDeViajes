package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Dominio.Pago;

import java.util.List;

public interface PagoService {
    public List<Pago> listaPagos();
    public void guardar (Pago pago);
    public void eliminar (Pago pago);
    public Pago encontrarPago(Pago pago);
}
