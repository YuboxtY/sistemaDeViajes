package SistemaDeViajes.sistema.Domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "encomiendas")
public class Encomienda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encomienda")
    private Long idEncomienda;

    @Column(name = "numero_de_envio", unique = true)
    private String numeroDeEnvio;

    // === Datos del Cliente ===
//    @ManyToOne
//    @JoinColumn(name = "remitente_id")
//    private Cliente remitente;
//
//    @ManyToOne
//    @JoinColumn(name = "destinatario_id")
//    private Cliente destinatario;

    // === Información del Envío ===
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    private String tipo;
    private String descripcion;

    // === Cálculo y Facturación ===
    private BigDecimal precioBase;
    private BigDecimal total;

    private EstadoEncomienda estado;

    public enum EstadoEncomienda {
        Pendiente,
        Enviado,
        Entregado,
        Cancelado
    }

    @Column(name = "fecha_factura")
    private LocalDateTime fechaFactura;

    private boolean pagado;
}
