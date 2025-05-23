package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dominio.Usuario;
import SistemaDeViajes.sistema.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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

    @GetMapping("/") //mapeo de la raiz
    public String inicio(Model model) { //metodo que se ejecutara cuando se haga una peticion
        return "index"; //retorna la vista index
    }

    @GetMapping("/agregarUsuario")
    public String agregarUsuario(Usuario user) { //metodo que se ejecutara cuando se haga una peticion
        return "/CrearUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardar(Usuario user) {
        usuarioService.guardar(user);
        return "redirect:/"; //redirecciona a la pagina principal
    }

    @GetMapping("/editarUsuario/{idUsuario}")
    public String editar(Usuario user, Model model) {
        user = usuarioService.encontrarUsuario(user);
        model.addAttribute("Usuario", user);
        return "/CrearUsuario";
    }

    @GetMapping("/eliminarUsuario")
    public String eliminar(Usuario user) {
        usuarioService.eliminar(user);
        return "redirect:/";
    }

    @GetMapping("/adminitrarRoles")
    public String Tabla(Model model) {
        var usuarios = usuarioService.listaUsuarios();
        model.addAttribute( "usuarios", usuarios);
        return "/AdminitrarRoles";
    }


}

