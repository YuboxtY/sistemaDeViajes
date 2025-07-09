package SistemaDeViajes.sistema.dao.services;

import SistemaDeViajes.sistema.Dominio.Rol;
import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public  class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
    @Autowired //inyeccion de depdendencia para instanciar la clase
    private UsuarioDao usuarioDao;
    @Override
    @Transactional(readOnly = true)//tranasaccion de lectura - no  afecta a la base de datos //comit guarda lo dle objeto a la base de datos//solo de lectura
    public List<Usuario> listaUsuarios() {
        return (List<Usuario>) usuarioDao.findAll();//encuentre de persona dao(capa de datos) todos los campos
    }


    @Override
    @Transactional//anotacion que sirve para indicar que el metodo es una transaccion de escritura
    // de la base de datos en la capa de servicio
    public void guardar(Usuario persona) {
        usuarioDao.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Usuario persona) {
        usuarioDao.delete(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrarUsuario(Usuario persona) {
        return usuarioDao.findById(persona.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public String encriptarPassword(String password) {
        // Crear una instancia de BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password); // Encriptar la contraseña
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByCedula(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        var roles = new ArrayList<GrantedAuthority>();
        Rol rol = usuario.getRol();
        if (rol != null) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.getCedula(), usuario.getPassword(), roles);
    }
//fin del metodo loadUserByUsername
}
