package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.dao.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ControladorUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/agregarUsuario")
    public String agregarUsuario(Usuario user) {
        return "Usuario/CrearUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardar(Usuario user) {
        usuarioService.encriptarPassword(user.getPassword());
        usuarioService.guardar(user);
        return "redirect:/";
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





