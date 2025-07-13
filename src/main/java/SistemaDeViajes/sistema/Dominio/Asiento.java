package SistemaDeViajes.sistema.Dominio;

import SistemaDeViajes.sistema.Domain.Unidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "asientos")
public class Asiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAsiento;

    @NotNull
    private String numero;

    @Column(nullable = true, length = 15)
    private String estado; // "disponible", "reservado", "comprado"

    @ManyToOne
    @JoinColumn(name = "idBoleto")
    private Boleto boleto;

    @ManyToOne
    @JoinColumn(name = "placa") // este nombre debe coincidir con tu columna FK
    private Unidad unidad;
}
