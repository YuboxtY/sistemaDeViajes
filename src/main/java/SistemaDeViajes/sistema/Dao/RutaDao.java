package SistemaDeViajes.sistema.dao;

import SistemaDeViajes.sistema.Domain.Ruta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RutaDao extends CrudRepository<Ruta, Long> { // Ruta es la entidad y Long es el tipo de dato del ID
    // No es necesario agregar métodos adicionales, ya que CrudRepository proporciona métodos básicos como save(), findById(), findAll(), deleteById(), etc.
    // Puedes agregar métodos personalizados si es necesario, pero para operaciones CRUD básicas, no es necesario.
}
