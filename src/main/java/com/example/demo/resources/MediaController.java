package com.example.demo.resources;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.generic.GenericRestController;
import com.example.demo.model.Midia;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.repository.MidiaUsuarioRepository;
import com.example.demo.service.KeycloakService;
import com.example.demo.service.MidiaService;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


//controller responsavel por salvar imagens
@RestController
@RequestMapping(path = "/midia", produces = "application/hal+json")
public class MediaController extends GenericRestController<Midia, MidiaRepository, MidiaService> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private KeycloakService keycloakService;


    @Autowired
    private MidiaUsuarioRepository midiaUsuarioRepository;

    @Override
    @PostMapping
    public ResponseEntity<Midia> save(
            Midia midia,
            HttpServletResponse response)
    {
        //TODO: mandar mensagem de erro caso imagem com mesmo nome ja exista
        Midia fromDb = service.save(midia);
        if(fromDb == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        //TODO: check for more errors before attempting to store image
        service.storeImage(midia);
        publisher.publishEvent(new RecursoCriadoEvento(this, response, fromDb.getID()));
        return ResponseEntity.status(HttpStatus.CREATED).body(fromDb);
    }

    @GetMapping(value = "/getImage/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable(name = "name") String name) throws IOException
    {
        ResponseEntity response = service.createResponseImage(name);
        return response;
    }

    //linka imagem com um usuario que realizou login
    @PostMapping(value = "/image/{midiaId}")
    public ResponseEntity<String> addMidiaUsuario(
            Principal principal,
            @PathVariable Long midiaId,
            HttpServletResponse response)
    {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String userEmail = (String) token.getTokenAttributes().get("email");

        //obtendo entidades
        Usuario usuario = usuarioService.get(userEmail);
        Midia midia = service.get(midiaId);

        //criando tabela de relação
        MidiaUsuario midiaUsuario = new MidiaUsuario();
        midiaUsuario.setMidia(midia);
        midiaUsuario.setUsuario(usuario);
        midiaUsuarioRepository.save(midiaUsuario);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}