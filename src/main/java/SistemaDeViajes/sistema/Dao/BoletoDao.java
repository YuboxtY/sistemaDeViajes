package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Boleto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoletoDao extends CrudRepository<Boleto, Long> {
    List<Boleto> findByUsuarioIdUsuario(Long idUsuario);
}
