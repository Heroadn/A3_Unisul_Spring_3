package com.example.demo.resources;

import com.example.demo.model.Usuario;
import com.example.demo.service.KeycloakService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @Autowired
    UsuarioService service;

    @GetMapping()
    public ResponseEntity<Usuario> getTest() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/oidc-principal")
    public OidcUser getOidcUserPrincipal(
            @AuthenticationPrincipal OidcUser principal) {
        return principal;
    }
    //TODO: mover para controller de usuario <<FUNÇÂO DE TESTE>>
    @GetMapping("/anonymous")
    public ResponseEntity<Usuario> getAnonymous(@RequestBody Usuario usuario) {
        /*
        Usuario usuarioBanco = service.save(usuario);

        //ele nao retorna mensagem de erro
        if(usuarioBanco == null)
            return ResponseEntity.notFound().build();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(usuarioBanco.getID())
          .toUri();

        return ResponseEntity.created(uri)
                .body(usuarioBanco);*/
        return ResponseEntity.ok().build();
    }

    @PostMapping("/anonymous1")
    public ResponseEntity<Usuario> getAnonymous1(@RequestBody Usuario usuario) {
        /*
        Usuario usuarioBanco = service.save(usuario);

        //ele nao retorna mensagem de erro
        if(usuarioBanco == null)
            return ResponseEntity.notFound().build();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(usuarioBanco.getID())
          .toUri();

        return ResponseEntity.created(uri)
                .body(usuarioBanco);*/
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    @PreAuthorize(value = "hasRole('admin')")
    public ResponseEntity<String> getAdmin(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("preferred_username");
        String userEmail = (String) token.getTokenAttributes().get("email");
        return ResponseEntity.ok("Hello Admin \nUser Name : " + userName + "\nUser Email : " + userEmail);
    }

    @GetMapping("/user")
    @PreAuthorize(value = "hasRole('user')")
    public ResponseEntity<String> getUser(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("preferred_username");
        String userEmail = (String) token.getTokenAttributes().get("email");
        return ResponseEntity.ok("Hello User \nUser Name : " + userName + "\nUser Email : " + userEmail);
    }

    //@GetMapping("/register")
    //@PreAuthorize(value = "permitAll()")
    //public ResponseEntity<String> registerTest(Principal principal) {
        /*
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userName = (String) token.getTokenAttributes().get("preferred_username");
        String userEmail = (String) token.getTokenAttributes().get("email");
        */

    //    service.createAccount("Teste", "Teste");
    //    return ResponseEntity.ok("Hello");
    //}

}
