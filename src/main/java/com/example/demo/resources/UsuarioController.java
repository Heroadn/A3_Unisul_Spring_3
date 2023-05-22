package com.example.demo.resources;

import com.example.demo.generic.GenericRestController;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioDTO;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping(value = "/meu-usuario")
    public ResponseEntity<UsuarioDTO> meuUsuario(
            Principal principal)
    {
        Usuario usuario = service.getByToken((JwtAuthenticationToken) principal);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setImages(service.getLinkImages(usuario));
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTO);
    }
}
