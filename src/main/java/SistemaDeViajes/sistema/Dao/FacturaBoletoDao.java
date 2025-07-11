package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Factura_Boleto;
import org.springframework.data.repository.CrudRepository;

public interface FacturaBoletoDao extends CrudRepository<Factura_Boleto, Long> {
}
