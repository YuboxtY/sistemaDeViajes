package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "boletos")
public class Boleto implements Serializable {

    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBoleto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario; // Usuario que compra el boleto
    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "idAsiento")

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTurno")
    private Turno turno; // Turno del viaje para el cual se compra el boleto

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idAsiento")
    private List<Asiento> asientos; // Lista de asientos reservados en el boleto

}
