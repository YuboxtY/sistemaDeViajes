package SistemaDeViajes.sistema.dao;

import SistemaDeViajes.sistema.Domain.Unidad;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnidadDao extends CrudRepository <Unidad, String> {

    List<Unidad> findByEstado(Unidad.estadoUnidad estado);

    // Unidad es la entidad y String es el tipo de dato del ID
    // No es necesario agregar métodos adicionales, ya que CrudRepository proporciona métodos básicos como save(), findById(), findAll(), deleteById(), etc.
    // Puedes agregar métodos personalizados si es necesario, pero para operaciones CRUD básicas, no es necesario.

}
