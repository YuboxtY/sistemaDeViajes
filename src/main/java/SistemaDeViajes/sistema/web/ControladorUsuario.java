package SistemaDeViajes.sistema.web;

import SistemaDeViajes.sistema.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //anotacion para indicar que esta clase es un controlador, un controlador es
//una clase que se encarga de recibir las peticiones del cliente y devolver una respuesta
//en este caso la respuesta sera una vista, en este caso una pagina html
@Slf4j //anotacion para indicar que esta clase es un controlador y que se va a utilizar el logger, un
//logger es una herramienta que nos permite registrar mensajes en un archivo de log y un archivo log es
//un archivo que contiene un registro de los eventos que ocurren en una aplicacion de
public class ControladorUsuario {
    @Autowired  //anotacion que permite inyectar la dependencia de la clase UsuarioService,
    //una dependencia es una clase que se necesita para que otra clase funcione, en este caso la clase UsuarioService
    private UsuarioService usuarioService;

@GetMapping("/agregarUsuario") //anotacion que indica que este metodo se ejecutara cuando se haga una peticion

    public String agregarUsuario() { //metodo que se ejecutara cuando se haga una peticion
        return "agregarUsuario"; //retorna la vista agregarUsuario.html
    } //fin del metodo agregarUsuario
}

