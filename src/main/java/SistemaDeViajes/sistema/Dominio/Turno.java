package SistemaDeViajes.sistema.Dominio;

import SistemaDeViajes.sistema.Domain.Ruta;
import SistemaDeViajes.sistema.Domain.Unidad;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name= "Turnos")
public class Turno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idRuta")
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "placa")
    private Unidad unidad;

    @OneToMany(mappedBy = "turno", cascade = CascadeType.ALL)
    private List<Boleto> boletos;

    private LocalDate fecha;
    private LocalTime hora;
}

