package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.Dao.services.UsuarioServiceImpl;
import SistemaDeViajes.sistema.Dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user-web")
public class PerfilController {

        @Autowired
        private UsuarioServiceImpl usuarioRepository;

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Principal principal) {
        String cedula = principal.getName(); // Aquí accedes a la cédula
        Usuario usuario = usuarioRepository.findByCedula(cedula).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "user-web/perfil"; // Vista en: templates/usuario/perfil.html
    }

    @PostMapping("/perfil")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario datosActualizados, Principal principal) {
        String cedula = principal.getName();
        Usuario usuario = usuarioRepository.findByCedula(cedula).orElseThrow();

        usuario.setNombre(datosActualizados.getNombre());
        usuario.setApellido(datosActualizados.getApellido());
        usuario.setEmail(datosActualizados.getEmail());

        usuarioRepository.guardar(usuario);
        return "redirect:/user-web/perfil?success";
    }

}
