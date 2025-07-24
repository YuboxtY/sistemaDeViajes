package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.services.RolServices;
import SistemaDeViajes.sistema.Dominio.Rol;
import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.Dao.RolDao;
import SistemaDeViajes.sistema.Dao.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ControladorUsuario {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolDao rolRepository;

    @Autowired
    private RolServices rolServices;

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

    @GetMapping("/crearPersonal")
    public String crearPersonal(Model model) {
        model.addAttribute("usuario", new Usuario()); // Asegúrate de que el objeto Usuario esté disponible
        return "Usuario/BotonCrearPersonal"; // Asegúrate de que la ruta sea correcta
    }


    @PostMapping("/guardarPersonal")
    public String guardarPersonal(@ModelAttribute("usuario") Usuario user,
                                  Model model) {
        // Verificar si el rol está presente en el objeto usuario
        if (user.getRol() == null) {
            model.addAttribute("error", "El rol es obligatorio.");
            return "Usuario/BotonCrearPersonal"; // Redirigir al formulario con un mensaje de error
        }
        // Suponiendo que el rol se envía como un ID (Long)
        Long rolId = user.getRol().getIdRol(); // Obtener el ID del rol desde el objeto Rol
        Optional<Rol> rolUsuario = rolRepository.findById(rolId);
        Rol rol = rolUsuario.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRol(rol); // Establecer el objeto Rol en el usuario
        user.setPassword(usuarioService.encriptarPassword(user.getCedula())); // ¡Muy importante!
        usuarioService.guardar(user);
        return "redirect:/index";
    }

    @PostMapping("/actualizarRol")
    public String actualizarRol(@RequestParam("usuarioId") Long usuarioId,
                                @RequestParam("rolId") Long rolId) {

        Usuario usuario = usuarioService.encontrarPorId(usuarioId);
        Rol nuevoRol = rolServices.encontrarPorId(rolId);

        if (usuario != null && nuevoRol != null) {
            usuario.setRol(nuevoRol);
            usuarioService.guardar(usuario);
        }

        return "redirect:/administrarRoles";
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
        return "redirect:/administrarRoles"; // Redirige a la página de administración de roles
    }
    @GetMapping("/gestionarUsuarios")
    public String gestionarUsuarios(@RequestParam(value = "termino", required = false) String termino,
                                    Model model) {
        List<Usuario> usuarios;

        if (termino != null && !termino.trim().isEmpty()) {
            usuarios = usuarioService.buscarPorCedulaONombre(termino.trim());
        } else {
            usuarios = usuarioService.listarTodos();
        }

        model.addAttribute("usuarios", usuarios);
        return "Usuario/AdministrarRoles";
    }



    @GetMapping("/administrarRoles")
    public String Tabla(Model model) {
        var usuarios = usuarioService.listaUsuarios();

        model.addAttribute("usuarios", usuarios);
        return "Usuario/AdministrarRoles";
    }
    @ModelAttribute("uri")
    public String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:messages");
        source.setDefaultEncoding("UTF-8"); // esto es obligatorio
        return source;
    }

}





