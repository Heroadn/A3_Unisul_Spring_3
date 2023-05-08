package com.example.demo.resources;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.generic.GenericRestController;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/usuario", produces = "application/hal+json")
public class UsuarioController extends GenericRestController<Usuario, UsuarioRepository, UsuarioService> {

    /*TODO: adicionar metodo de atualização de login, caso os dados de senha
    tenham sido perdidos no servidor de authenticação */

    //TODO: adicionar metodo para refresh token
    /*TODO: fazer com q login retorne um refresh token, e com esse token
    posso pedir um access token sempre q expirado*/
    /*
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Usuario model, HttpServletResponse response) {
        String token = service.login(model);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }*/

    //metodo retorna um refreshToken
    @PostMapping(value = "/login-refresh")
    public ResponseEntity<String> loginRefreshToken(@RequestBody Usuario model, HttpServletResponse response) {
        String token = service.login(model);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping(value = "/login-access")
    public ResponseEntity<String> loginAccessToken(String refreshToken, HttpServletResponse response) {
        String token = service.getAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
