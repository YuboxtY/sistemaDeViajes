package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Domain.Encomienda;

import java.util.List;

public interface EncomiendaServices {

    public List <Encomienda> listarEncomiendas(); // Listar todas las encomiendas
    public void guardar(Encomienda encomienda); // Guardar una encomienda
    public void eliminar(Encomienda encomienda); // Eliminar una encomienda
    public Encomienda encontrarEncomienda(Encomienda encomienda); // Encontrar una encomienda por su ID
}
