package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name= "roles") // Asumiendo que la tabla se llama "roles"
public class Rol {
    private static final long serialVersionUID = 1L; // sirve para la serializacion de la clase que es
    // una interfaz que permite convertir un objeto en una secuencia de bytes
    @Id //anotacion para indicar que es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //anotacion que genera automaticamente el id,
    // en este caso es autoincremental que
    // queire decir que cada vez que se inserte un nuevo registro, el id se incrementa automaticamente
    private long idRol;

    @NotEmpty //anotacion para indicar que no puede ser nulo
    private String nombre;
}
