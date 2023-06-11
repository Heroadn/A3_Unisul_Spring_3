package com.example.demo.service;

import com.example.demo.generic.BasicRestDTOService;
import com.example.demo.generic.BasicRestService;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Pokemon;
import com.example.demo.model.PokemonDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PokemonRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonService extends BasicRestDTOService<Pokemon, PokemonDTO, PokemonRepository> {

    @Autowired
    PokemonRepository pokemonRepository;

    @Override
    public PokemonDTO update(Pokemon resource, Long id, String[] ignoredProperties, HttpServletRequest request) {
        Pokemon oldPokemon = pokemonRepository.findById(id).orElseThrow(
                () -> new DataIntegrityViolationException("ID invalido"));

        resource.setID(oldPokemon.getID());
        resource.setData_criacao(oldPokemon.getData_criacao());
        return toDTO(pokemonRepository.save(resource));
    }

    @Override
    public PokemonDTO toDTO(Pokemon resource) {
        return new PokemonDTO(resource);
    }

    @Override
    public <T> Boolean exists(T condition)
    {
        return pokemonRepository.existsByNome((String) condition);
    }

    public List<Pokemon> findAllByNome(String nome)
    {
        //TODO: adicionar paginação e provavelmente mover para find all
        return pokemonRepository.findAllByNome(nome);
    }

}