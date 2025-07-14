package SistemaDeViajes.sistema.Domain;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "rutas")
public class Ruta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Long idRuta;


    private String origen;
    private String destino;


//    private String horario;
//
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
//    @JoinTable(
//            name = "ruta_unidad",
//            joinColumns = @JoinColumn(name = "ruta_id"),
//            inverseJoinColumns = @JoinColumn(name = "unidad_id")
//    )
//    private List<Unidad> unidades; // Relación con la entidad Unidad



}
