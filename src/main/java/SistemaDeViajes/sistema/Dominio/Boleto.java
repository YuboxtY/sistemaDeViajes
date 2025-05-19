package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "boletos")
public class Boleto implements Serializable {

    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBoleto;
    private String Descripcion;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String ruta;
    private String fecha;
    private String horario;
    private String asiento;
    private String tarifa;
}
