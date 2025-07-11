package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Boleto;
import SistemaDeViajes.sistema.Dominio.Turno;

import java.util.List;

public interface TurnoService  {

    public List<Turno> listaBoleto();
    public void guardar (Turno turno);
    public void eliminar (Turno turno);
    public Turno encontrarTurno(Turno turno);
}
