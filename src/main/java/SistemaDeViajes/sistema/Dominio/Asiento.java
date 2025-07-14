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

    @Column(length = 15)
    private String estado; // "disponible", "reservado", "no disponible"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBoleto") // FK correcta hacia Boleto
    private Boleto boleto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placa", referencedColumnName = "placa") // Asegura que coincida con @Id de Unidad
    private Unidad unidad;
}

