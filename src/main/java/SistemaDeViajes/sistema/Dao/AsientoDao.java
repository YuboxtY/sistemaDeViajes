package SistemaDeViajes.sistema.Dao;

import SistemaDeViajes.sistema.Dominio.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsientoDao extends JpaRepository<Asiento, Long>, CrudRepository<Asiento, Long> {

    // Contar asientos por estado y placa
    @Query("SELECT COUNT(a) FROM Asiento a WHERE a.unidad.placa = :placa AND a.estado = :estado")
    int countByUnidadPlacaAndEstado(@Param("placa") String placa, @Param("estado") String estado);

    // Listar asientos por estado y placa
    @Query("SELECT a FROM Asiento a WHERE a.unidad.placa = :placa AND a.estado = :estado")
    List<Asiento> findByUnidadPlacaAndEstado(@Param("placa") String placa, @Param("estado") String estado);

    // Listar todos los asientos por placa (sin filtrar estado)
    @Query("SELECT a FROM Asiento a WHERE a.unidad.placa = :placa")
    List<Asiento> findByUnidadPlaca(@Param("placa") String placa);
}
