package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Domain.Encomienda;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncomiendaDao extends CrudRepository<Encomienda, Long> {
    List<Encomienda> findByRemitenteCedula(String cedula); // Método para buscar encomiendas por cédula del remitente


}
