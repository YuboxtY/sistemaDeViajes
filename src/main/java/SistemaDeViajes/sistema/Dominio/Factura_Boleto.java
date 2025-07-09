package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "facturasdeBoletos")
public class Factura_Boleto implements Serializable
{
    private static final long serialVersionUID = 1l; // Constante long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBoleto;
    private String descripcion;
    private String nro_factura;
    private String Cedula_cli;
    private String Nombre_cli;
    private String Apellido_cli;

    private String Fecha_fact;
    private double sub_fact;
    private double iva_fact;
    private double total_fact;

    private String asiento;
    private String Ruta;
}
