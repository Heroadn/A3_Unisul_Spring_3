package com.example.demo.resources;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.generic.GenericRestController;
import com.example.demo.model.Midia;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.MidiaService;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuario", produces = "application/hal+json")
public class UsuarioController extends GenericRestController<Usuario, UsuarioRepository, UsuarioService> {

    //metodo retorna um refreshToken
    @PostMapping(value = "/login-refresh")
    public ResponseEntity<String> loginRefreshToken(
            @RequestBody Usuario model,
            HttpServletResponse response)
    {
        String token = service.login(model);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    //recebe um refresh token e retorna um access token valido
    @PostMapping(value = "/login-access")
    public ResponseEntity<String> loginAccessToken(
            String refreshToken,
            HttpServletResponse response)
    {
        String token = service.getAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
