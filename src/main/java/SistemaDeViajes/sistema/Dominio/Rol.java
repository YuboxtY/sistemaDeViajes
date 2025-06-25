package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name= "roles") // Asumiendo que la tabla se llama "roles"
public class Rol {
    private static final long serialVersionUID = 1L; // Constante para la serialización
    private long idRol; // Identificador del rol
    private String nombreRol; // Nombre del rol, por ejemplo: "ADMIN", "USER", etc.

    // Puedes agregar más campos según sea necesario, como descripciones o permisos asociados al rol.
}
