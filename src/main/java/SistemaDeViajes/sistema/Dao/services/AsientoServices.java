package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Asiento;

import java.util.List;

public interface AsientoServices {
    public List<Asiento> listaBoleto();
    public void guardar (Asiento asiento);
    public void eliminar (Asiento asiento);
    public Asiento encontrarAsientos(Asiento asiento);
    public List<Asiento> obtenerAsientosDisponibles(String placa);

}
