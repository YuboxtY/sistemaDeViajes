package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Ordenes de Pago")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPago;
    private String num_fact;
    private String fech_pag;
    private double mont_pag;
    private String metod_pag;
    private String est_pag;
    private String obs_pag;

}
