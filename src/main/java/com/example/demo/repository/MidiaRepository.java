package com.example.demo.repository;

import com.example.demo.model.Midia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface MidiaRepository extends JpaRepository<Midia, Serializable> {
    Optional<Midia> findByFileName(String name);
}
