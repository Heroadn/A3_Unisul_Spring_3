package com.example.demo.repository;

import com.example.demo.model.Pokemon;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Serializable> {
    Boolean existsByNome(String nome);

    List<Pokemon> findAllByNome(String nome);
}