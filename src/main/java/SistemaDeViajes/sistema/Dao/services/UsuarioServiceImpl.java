package SistemaDeViajes.sistema.Dao.services;

import SistemaDeViajes.sistema.Dominio.Cliente;
import SistemaDeViajes.sistema.Dominio.Rol;
import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.Dao.UsuarioDao;
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
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
    @Autowired //inyeccion de depdendencia para instanciar la clase
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
//tranasaccion de lectura - no  afecta a la base de datos //comit guarda lo dle objeto a la base de datos//solo de lectura
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
    public Optional<Usuario> findByCedula(String cedula) {
        return usuarioDao.findByCedula(cedula);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioDao.findByCedula(username);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        var roles = new ArrayList<GrantedAuthority>();
        Rol rol = usuario.get().getRol();
        if (rol != null) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.get().getCedula(), usuario.get().getPassword(), roles);
    }

    //fin del metodo loadUserByUsername
    public Optional<Cliente> convertirUsuarioAClientePorCedula(String cedula) {
        Optional<Usuario> usuarioOpt = usuarioDao.findByCedula(cedula);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Cliente cliente = new Cliente();
            cliente.setCedula(usuario.getCedula());
            cliente.setNombre(usuario.getNombre());
            cliente.setApellido(usuario.getApellido());
            cliente.setCorreo(usuario.getEmail());
            return Optional.of(cliente);
        }

        return Optional.empty();
    }


}
