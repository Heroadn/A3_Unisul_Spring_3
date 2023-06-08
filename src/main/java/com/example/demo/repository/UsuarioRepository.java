package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Serializable> {

    Boolean existsByEmail(String email);

    Boolean existsByNome(String nome);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNome(String nome);
}