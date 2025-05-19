package SistemaDeViajes.sistema.Domain;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table (name = "rutas")
public class Ruta implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id_ruta")
    private Long idRuta;

    private String origen;
    private String destino;
    private String horario;















    //private String fechaSalida;
   // private String fechaLlegada;
   // private String horaSalida;
   // private String horaLlegada;
  //  private String precio;
   // private String duracion;
   // private String tipoViaje;

    // Constructor, getters y setters

}
