package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "personas")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCliente;
    private String cedula;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;

    @Column(name="email", nullable= true)
    @Email
    private String email;

    private String telefono;
}

