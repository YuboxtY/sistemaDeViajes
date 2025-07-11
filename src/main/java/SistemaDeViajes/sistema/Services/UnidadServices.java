package SistemaDeViajes.sistema.Services;

import SistemaDeViajes.sistema.Domain.Unidad;

import java.util.List;

public interface UnidadServices {
    List<Unidad> listarUnidades();
    List<Unidad> listarUnidadesDisponibles();
    void guardar(Unidad unidad);
    void eliminar(Unidad unidad);
    void actualizarUnidad(Unidad unidad);
    Unidad encontrarUnidad(Unidad unidad);
    Unidad encontrarPorPlaca(String placa);
}
