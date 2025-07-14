package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dominio.Asiento;
import SistemaDeViajes.sistema.Dominio.Turno;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data // Lombok para generar getters y setters automáticamente
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "unidades") // Nombre de la tabla en la base de datos
public class Unidad implements Serializable { // Serializable para permitir la serialización de objetos

    @Id // Indica que este campo es la clave primaria
    @Column(name = "placa", length = 10, unique = true)
    private String placa; // Placa de la unidad

    private String modelo; // Modelo de la unidad

    private Integer capacidad; // Capacidad de la unidad

    @Enumerated(EnumType.STRING) // Indica que el campo es un enumerado y se almacenará como cadena
    private estadoUnidad estado; // Estado de la unidad (disponible, asignada, en mantenimiento)

    @OneToMany(mappedBy = "unidad", cascade = CascadeType.ALL)
    private List<Turno> turnos;

    @OneToMany(mappedBy = "unidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asiento> asientos;

    public enum estadoUnidad {
        Disponible,
        Asignada,
        EnMantenimiento
    }
}