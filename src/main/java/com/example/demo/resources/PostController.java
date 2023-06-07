package com.example.demo.resources;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.generic.GenericRestController;
import com.example.demo.model.Midia;
import com.example.demo.model.Post;
import com.example.demo.model.Usuario;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.repository.MidiaUsuarioRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.KeycloakService;
import com.example.demo.service.MidiaService;
import com.example.demo.service.PostService;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Bruxaria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


//controller responsavel por salvar imagens
@RestController
@RequestMapping(path = "/post", produces = "application/hal+json")
public class PostController extends GenericRestController<Post, PostRepository, PostService> {

    //mover para postService
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<Post> save(
            Post post,
            HttpServletResponse response,
            HttpServletRequest request)
    {
        //pegando token do usuario q fez a requisição e o adicionando como criador do post
        Principal principal = request.getUserPrincipal();
        Usuario usuario = usuarioService.getByToken((JwtAuthenticationToken) principal);
        post.setUsuario(usuario);

        return super.save(post, response, request);
    }

    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<Page<Post>> findAllByUsuario(
            Pageable pageable,
            @PathVariable(name = "id") Long usuarioId) {

        Page<Post> list = postService.findAllPostsByUsuarioID(usuarioId, pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

}