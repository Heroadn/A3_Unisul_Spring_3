package com.example.demo.service;

import com.example.demo.generic.BasicRestDTOService;
import com.example.demo.generic.BasicRestService;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioDTO;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.resources.MidiaController;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService extends BasicRestDTOService<Usuario, UsuarioDTO, UsuarioRepository> {
    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private MidiaService midiaService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UsuarioDTO save(Usuario usuario) {
        if (exists(usuario.getEmail()))
            return null;

        //Configurando usuario
        usuario.setSenha(usuario.getSenha());
        usuario.setToken(generteRandomCode(7));
        Usuario usuarioBanco = usuarioRepository.save(usuario);

        //TODO: usar optionals
        /*usuario.ifPresent(u -> {*/

        //store passwords and others into auth server
        int response = keycloakService.createAccount(usuarioBanco);

        //if the user already exists in keycloaker
        if (response == HttpStatus.CONFLICT.value()) {
            usuarioRepository.delete(usuario);
            throw new DataIntegrityViolationException("User already exists in auth server");
        }

        //if the auth server could not create
        //delete it from the resource server
        if (response != HttpStatus.CREATED.value()) {
            System.out.println("Could not create user");
            usuarioRepository.delete(usuario);
            return null;
        }

        return toDTO(usuarioBanco);
    }

    @Override
    public UsuarioDTO delete(Long id) {
        super.delete(id);
        //TODO: remover dados do servidor de autenticação, quando remover usuario
        return null;
    }

    @Override
    public <T> Boolean exists(T condition) {
        return usuarioRepository.existsByEmail((String)condition);
    }

    @Override
    public UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setImages(this.getLinkImages(usuario));
        return usuarioDTO;
    }

    public Usuario getByEmail(String email) {
        Optional<Usuario> resource = usuarioRepository.findByEmail(email);
        resource.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return resource.get();
    }

    public Usuario getByToken(JwtAuthenticationToken token) {
        String userEmail = (String) token.getTokenAttributes().get("email");
        Usuario usuario = this.getByEmail(userEmail);
        return usuario;
    }

    public Collection<String> getLinkImages(Usuario usuario) {
        Collection<String> links = new ArrayList<>();

        if(usuario.getImages() != null)
            for (MidiaUsuario mu : usuario.getImages()) {
                Link link = midiaService.toLink(mu.getMidia());
                links.add(link.getHref());
            }

        return links;
    }

    public String login(Usuario usuario) {
        //TODO: mensagem de erro ao login falhar
        //classe que gera exception ao falhar, javax.ws.rs.NotAuthorizedException: HTTP 401 Unauthorized
        AccessTokenResponse response = keycloakService.getAccessToken(usuario);
        return keycloakService.getAccessToken(usuario).getRefreshToken();
    }

    public String getAccessToken(String refreshToken) {
        //TODO: verificar por errors
        return keycloakService.requestAccessToken(refreshToken);
    }

    private String generteRandomCode(int size){
        return ""+ (size * Math.random());
    }
}