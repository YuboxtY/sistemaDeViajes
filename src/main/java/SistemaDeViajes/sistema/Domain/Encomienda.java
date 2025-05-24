//package SistemaDeViajes.sistema.Domain;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.io.Serializable;
//
//
//@Data
//@Entity
//@Table ( name = "encomiendas" ) // Nombre de la tabla en la base de datos
//public class Encomienda implements Serializable {
//
//    @Id
//    @GeneratedValue (strategy = GenerationType.IDENTITY)
//    @Column( name = "id_encomienda")
//    private String idEncomienda;
//
//    @Column ( name = "numero_de_envio", unique = true) // Número de envío único
//    private String numeroDeEnvio;
//
//    private String remitente; // Remitente de la encomienda
//    private String destinatario; // Destinatario de la encomienda
//
//    @Column ( name = "fecha_envio")
//    private String fechaEnvio; // Fecha de envío
//    private String tipo; // Tipo de encomienda (documento, paquete, etc.)
//    private String estado; // Estado de la encomienda (en tránsito, entregada, etc.)
//    private String precio; // Precio del envío
//
//
//
//}

