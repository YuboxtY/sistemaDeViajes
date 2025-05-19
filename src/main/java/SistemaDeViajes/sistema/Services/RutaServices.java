package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Domain.Ruta;
import java.util.List;


public interface RutaServices {
    // Métodos para la interfaz RutaServices
    public List<Ruta> listarRutas(); // Listar todas las rutas
    public void guardar(Ruta ruta); // Guardar una ruta
    public void eliminar(Ruta ruta); // Eliminar una ruta
    public Ruta encontrarRuta(Ruta ruta); // Encontrar una ruta por su ID
}
