package com.example.demo.resources;

import com.example.demo.generic.GenericRestController;
import com.example.demo.generic.GenericRestDTOController;
import com.example.demo.model.Pokemon;
import com.example.demo.model.PokemonDTO;
import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioDTO;
import com.example.demo.repository.PokemonRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.PokemonService;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/pokemon", produces = "application/hal+json")
public class PokemonController extends GenericRestDTOController<Pokemon, PokemonDTO, PokemonRepository, PokemonService> {

}
