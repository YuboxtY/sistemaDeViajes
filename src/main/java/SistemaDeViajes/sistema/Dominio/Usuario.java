package SistemaDeViajes.sistema.Dominio;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "usuarios")
public class Usuario extends Cliente {

    private String rol;
    private String user;
    private String password;

}
