package com.example.demo.service;

import com.example.demo.generic.BasicRestService;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;
import java.util.Optional;

//TODO: adicionar configurações do jwt
@Service
public class UsuarioService extends BasicRestService<Usuario, UsuarioRepository> {
    @Autowired
    KeycloakService keycloakService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario get(String email) {
        Optional<Usuario> resource = usuarioRepository.findByEmail(email);
        resource.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return resource.get();
    }

    @Override
    public Usuario save(Usuario usuario) {
        //Configurando usuario
        usuario.setSenha(usuario.getSenha());
        usuario.setToken(generteRandomCode(7));
        Usuario usuarioBanco = usuarioRepository.save(usuario);

        //TODO: check if the user already exists
        //if it already exist in the db return error

        //store passwords and others into auth server
        int response = keycloakService.createAccount(usuarioBanco);

        //if the user already exists
        if (response == HttpStatus.CONFLICT.value()) {
            //TODO: change to logger
            System.out.println("User already exists");
            usuarioRepository.delete(usuario);
            return null;
        }

        //if the auth server could not create
        //delete it from the resource server
        if (response != HttpStatus.CREATED.value()) {
            //TODO: change to logger
            System.out.println("Could not create user");
            usuarioRepository.delete(usuario);
            return null;
        }

        //TODO: guardar id gerado pelo servidor de autorização

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