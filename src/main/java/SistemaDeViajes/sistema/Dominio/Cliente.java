package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCliente;

    @Column(name = "cedula", unique = true, nullable = false, length = 10)
    private String cedula;

    @NotEmpty
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotEmpty
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Email
    @Column(name = "correo", nullable = true, length = 100)
    private String correo;

    @Column(name = "telefono", nullable = true, length = 10)
    private String telefono;
}
