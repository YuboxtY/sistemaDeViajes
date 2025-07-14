package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "boleto")
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

    @OneToMany(mappedBy = "boleto", cascade = CascadeType.ALL)
    private List<Asiento> asientos; // Lista de asientos reservados en el boleto
    private double subtotal; // Precio del boleto
    private double iva;
    private double total; // Total a pagar por el boleto
    private String fechaCompra;
    private String formaPago;
    // Fecha de compra del boleto
}
