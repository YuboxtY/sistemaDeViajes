package SistemaDeViajes.sistema.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user-web")
public class UserWebController {
    @Controller
    public class ControladorUsuario {

        // … tus mappings existentes …

        @GetMapping("/user-web/index")
        public String userIndex(Principal principal, Model model) {
            // para mostrar el nombre de usuario, si quieres
            model.addAttribute("username", principal.getName());
            return "user-web/index";
        }

        // opcional: un “catch-all” para redirigir /user-web al mismo index
        @GetMapping("/user-web")
        public String userWeb() {
            return "redirect:/user-web/index";
        }
    }


}





