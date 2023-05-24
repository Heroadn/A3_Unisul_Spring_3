package com.example.demo.resources;

import com.example.demo.generic.GenericRestController;
import com.example.demo.generic.GenericRestDTOController;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioDTO;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping(path = "/usuario", produces = "application/hal+json")
public class UsuarioController extends GenericRestDTOController<Usuario, UsuarioDTO,UsuarioRepository, UsuarioService> {

    //TODO: criar genericRestControllerDTO -> que trabalha com DTO
    //TODO: criar genericServiceDRTO com metodo q converte um Model a um ModelDTO
    /**
     * metodo que aceita as credencias de login,
     * e com isso retorna um refresh token que
     * dura mais tempo que um access token
     *
     * @param  usuario dados de login
     * @return         refresh token usado para obter access token
     */
    @PostMapping(value = "/login-refresh")
    public ResponseEntity<String> loginRefreshToken(
            @RequestBody Usuario usuario,
            HttpServletResponse response) {
        String token = service.login(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    /**
     * Token necessario para acessar apis com restrição de login
     * ele tambem contem privilegios de acesso como admin/user,
     * pode ser usar repetidamente ate o prazo do refreshToken
     *
     * @param  refreshToken obtido com dados de login do usuario
     * @return              access token usado para acessar apis com login
     */
    @PostMapping(value = "/login-access")
    public ResponseEntity<String> loginAccessToken(
            String refreshToken,
            HttpServletResponse response) {
        String token = service.getAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    /**
     * Increment a value by delta and return the new value.
     *
     * @param  principal    token jwt de acesso
     * @return              access token usado para acessar apis com login
     */
    @GetMapping(value = "/meu-usuario")
    public ResponseEntity<UsuarioDTO> meuUsuario(
            Principal principal) {
        Usuario usuario = service.getByToken((JwtAuthenticationToken) principal);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        usuarioDTO.setImages(service.getLinkImages(usuario));
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTO);
    }
}
