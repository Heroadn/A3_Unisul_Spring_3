package com.example.demo.service;

import com.example.demo.generic.BasicRestService;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends BasicRestService<Usuario, UsuarioRepository> {
    @Autowired
    KeycloakService keycloakService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public Usuario save(Usuario usuario) {
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
            System.out.println("User already exists in auth server");
            return null;
        }

        //if the auth server could not create
        //delete it from the resource server
        if (response != HttpStatus.CREATED.value()) {
            System.out.println("Could not create user");
            usuarioRepository.delete(usuario);
            return null;
        }

        //removing secret fields
        usuarioBanco.setToken("******");
        usuarioBanco.setSenha("******");
        return usuarioBanco;
    }

    //TODO: remover dados do servidor de autenticação, quando remover usuario
    @Override
    public Usuario delete(Long id) {
        super.delete(id);
        //remover do servidor de autenticação
        return null;
    }

    @Override
    public <T> Boolean exists(T condition) {
        return usuarioRepository.existsByEmail((String)condition);
    }

    public Usuario getByEmail(String email) {
        Optional<Usuario> resource = usuarioRepository.findByEmail(email);
        resource.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return resource.get();
    }


    public String login(Usuario usuario)
    {
        //TODO: mensagem de erro ao login falhar
        //classe que gera exception ao falhar, javax.ws.rs.NotAuthorizedException: HTTP 401 Unauthorized
        AccessTokenResponse response = keycloakService.getAccessToken(usuario);
        return keycloakService.getAccessToken(usuario).getRefreshToken();
    }

    public String getAccessToken(String refreshToken)
    {
        //TODO: verificar por errors
        return keycloakService.requestAccessToken(refreshToken);
    }


    private String generteRandomCode(int size){
        return ""+ (size * Math.random());
    }
}