package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dominio.Rol;
import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.dao.services.RolDao;
import SistemaDeViajes.sistema.dao.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Controller
@Slf4j
public class ControladorUsuario {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolDao rolRepository;

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Nombre de la plantilla SIN extensión
    }

    @GetMapping("/crearCuenta")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario()); // o Persona, según tu clase
        return "Usuario/BotonCrearUsuario"; // la ruta al archivo HTML
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario user,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "Usuario/CrearUsuario";
        }
        Optional<Rol> rolUsuario = rolRepository.findById(1L);
        Rol rol = rolUsuario.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRol(rol);
        user.setPassword(usuarioService.encriptarPassword(user.getPassword())); // ¡Muy importante!
        usuarioService.guardar(user);
        // guardar usuario, etc.
        return "redirect:/login";
    }


    @GetMapping("/editarUsuario/{idUsuario}")
    public String editar(Usuario user, Model model) {
        user = usuarioService.encontrarUsuario(user);
        model.addAttribute("usuario", user);
        return "Usuario/CrearUsuario";
    }

    @GetMapping("/eliminarUsuario")
    public String eliminar(Usuario user) {
        usuarioService.eliminar(user);
        return "redirect:/";
    }

    @GetMapping("/administrarRoles")
    public String Tabla(Model model) {
        var usuarios = usuarioService.listaUsuarios();

        model.addAttribute("usuarios", usuarios);
        return "Usuario/AdministrarRoles";
    }
}





