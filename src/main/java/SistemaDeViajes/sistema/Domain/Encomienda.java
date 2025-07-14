package SistemaDeViajes.sistema.Domain;

import SistemaDeViajes.sistema.Dominio.Cliente;
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

    @Column(name = "numero_de_envio", unique = true, nullable = false)
    private String numeroDeEnvio;


    // Remitente (obligatorio, cascada)
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            optional = false)
    @JoinColumn(name = "remitente_id", nullable = false)
    private Cliente remitente;

    // Destinatario (obligatorio, cascada)
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            optional = false)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Cliente destinatario;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    public enum EstadoEncomienda { Pendiente, Enviado, Entregado, Cancelado }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEncomienda estado;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    public enum MetodoPago { EFECTIVO, TARJETA, TRANSFERENCIA }

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "precio_subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioSubtotal;

    @Column(name = "iva", precision = 10, scale = 2, nullable = false)
    private BigDecimal iva;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "fecha_factura", nullable = false)
    private LocalDateTime fechaFactura;

    private boolean pagado;

    @Column(length = 500)
    private String descripcion;

    @PrePersist
    @PreUpdate
    private void calcularCampos() {
        if (precioSubtotal != null) {
            this.iva = precioSubtotal.multiply(new BigDecimal("0.12"));
            this.total = precioSubtotal.add(this.iva);
        }
        this.fechaFactura = this.fechaEnvio;
    }
}
