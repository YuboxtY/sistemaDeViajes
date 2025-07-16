package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dao.RolDao;
import SistemaDeViajes.sistema.Dominio.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Rolimplemts implements RolServices{

    @Autowired
    private RolDao rolDao;

    @Override
    public List<Rol> listarRoles() {
        return rolDao.findAll();
    }

    @Override
    public Rol encontrarPorId(Long idRol) {
        return rolDao.findById(idRol).orElse(null);
    }
}
