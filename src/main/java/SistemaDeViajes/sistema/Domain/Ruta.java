package SistemaDeViajes.sistema.Domain;


import SistemaDeViajes.sistema.Dominio.Turno;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
    private String horario;


    //private List<Unidad> unidades; // Relación con la entidad Unidad

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    private List<Turno> turnos;



}
