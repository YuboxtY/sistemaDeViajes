package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.ClienteDao;
import SistemaDeViajes.sistema.Dao.UsuarioDao;
import SistemaDeViajes.sistema.Dominio.Cliente;
import SistemaDeViajes.sistema.Dominio.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping ("/clientes")
public class ClienteController {
    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private UsuarioDao usuarioDao;




    @GetMapping("/buscar")
    public ResponseEntity<Cliente> buscarPorCedula(@RequestParam String cedula) {
        // Buscar primero como Cliente
        Optional<Cliente> clienteOpt = clienteDao.findByCedula(cedula);
        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        }

        // Si no existe como Cliente, buscar en Usuario
        Optional<Usuario> usuarioOpt = usuarioDao.findByCedula(cedula);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setCedula(usuario.getCedula());
            nuevoCliente.setNombre(usuario.getNombre());
            nuevoCliente.setApellido(usuario.getApellido());
            nuevoCliente.setCorreo(usuario.getEmail());
            return ResponseEntity.ok(nuevoCliente);
        }

        // No encontrado
        return ResponseEntity.notFound().build();
    }


}
