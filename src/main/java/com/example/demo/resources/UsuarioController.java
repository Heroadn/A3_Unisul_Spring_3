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

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Usuario model, HttpServletResponse response) {
        String token = service.login(model);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
