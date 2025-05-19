package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Domain.Unidad;
import java.util.List;

public interface UnidadServices {

    // Métodos para la interfaz UnidadServices
    public List<Unidad> listarUnidades(); // Listar todas las unidades
    public void guardar (Unidad unidad); // Guardar una unidad
    public void eliminar (Unidad unidad); // Eliminar una unidad
    public Unidad encontrarUnidad (Unidad unidad); // Encontrar una unidad por su ID
}
