package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.List;


@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;
    private String cedula;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    @Email
    private String email;
    private String password;

    @OneToMany
    @JoinColumn(name = "id_usuario") // Nombre de la columna que se va a relacionar
    private List<Rol> rol; // Relación con la entidad Rol, asumiendo que existe una clase Rol definida en el mismo paquete.



}

