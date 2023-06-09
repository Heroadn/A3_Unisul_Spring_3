package com.example.demo.resources;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.generic.GenericRestController;
import com.example.demo.model.Midia;
import com.example.demo.model.Usuario;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.repository.MidiaUsuarioRepository;
import com.example.demo.service.KeycloakService;
import com.example.demo.service.MidiaService;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Bruxaria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


//controller responsavel por salvar imagens
@RestController
@RequestMapping(path = "/midia", produces = "application/hal+json")
public class MidiaController extends GenericRestController<Midia, MidiaRepository, MidiaService> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private MidiaUsuarioRepository midiaUsuarioRepository;


    //TODO: mover como midiaServiço
    @Override
    @PostMapping
    public ResponseEntity<Midia> save(
            Midia midia,
            HttpServletResponse response,
            HttpServletRequest request)
    {
        Midia fromDb = service.save(midia);
        if(fromDb == null)
            throw new DataIntegrityViolationException("Recurso já existe");

        service.createImage(midia);
        publisher.publishEvent(new RecursoCriadoEvento(this, response, fromDb.getID()));
        return ResponseEntity.status(HttpStatus.CREATED).body(fromDb);
    }

    @GetMapping(value = "/static/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable(name = "name") String name) throws IOException
    {
        return service.createResponseImage(name);
    }

    //linka imagem com um usuario que realizou login
    @PostMapping(value = "/usuario")
    public ResponseEntity<Midia> addMidiaUsuario(
            @RequestBody Midia midia,
            Principal principal,
            HttpServletResponse response,
            HttpServletRequest request)
    {
        Usuario usuario = usuarioService.getByToken((JwtAuthenticationToken) principal);
        midia.setFileName(service.createUUID(midia));

        //usando rota de salvamento de imagem e adicionado link de acesso
        ResponseEntity<Midia> resMidia = save(midia, response, request);
        String ext = Bruxaria.getBase64Ext(midia.getFileImage64());
        resMidia.getBody().setFileName(midia.getFileName() + "." + ext);
        resMidia.getBody().add(service.toLink(resMidia.getBody()));
        resMidia.getBody().setFileImage64("****");

        //criando tabela de relação
        service.saveMidiaUsuario(usuario, resMidia.getBody());
        return ResponseEntity.status(HttpStatus.CREATED).body(midia);
    }

}