package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Domain.Unidad;
import org.springframework.data.repository.CrudRepository;

public interface UnidadDao extends CrudRepository <Unidad, String> { // Unidad es la entidad y String es el tipo de dato del ID
    // No es necesario agregar métodos adicionales, ya que CrudRepository proporciona métodos básicos como save(), findById(), findAll(), deleteById(), etc.
    // Puedes agregar métodos personalizados si es necesario, pero para operaciones CRUD básicas, no es necesario.

}
