package SistemaDeViajes.sistema.Dominio;


import SistemaDeViajes.sistema.Domain.Unidad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name= "asientos")
public class Asiento implements Serializable {

    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAsiento;
    @NotNull
    private String numero; // Número del asiento, por ejemplo "1A", "2B", etc.
    @NotNull
    private boolean disponible; // Estado del asiento, por ejemplo "disponible", "reservado", "ocupado"
    @ManyToOne
    @JoinColumn(name = "idBoleto")
    private Boleto boleto; // Asumiendo que tienes una entidad Unidad que representa la unidad de transporte

    @ManyToOne
    @JoinColumn(name = "placa")
    private Unidad unidad;

}
