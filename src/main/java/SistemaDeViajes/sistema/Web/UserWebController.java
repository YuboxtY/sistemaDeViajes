package SistemaDeViajes.sistema.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user-web")
public class UserWebController {

    // GET  /user-web        -> redirige a /user-web/index
    @GetMapping
    public String redirectToIndex() {
        return "redirect:/user-web/index";
    }

    // GET  /user-web/index  -> vista index.html bajo templates/user-web/
    @GetMapping("/index")
    public String userIndex(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user-web/index";
    }

    // GET  /user-web/contacto -> vista contacto.html bajo templates/user-web/
    @GetMapping("/contacto")
    public String contacto() {
        return "user-web/contacto";
    }

    // (Opcional) GET /user-web/404 si lo quieres llamar manualmente
    @GetMapping("/404")
    public String userWeb404() {
        return "error/404";
    }

}






