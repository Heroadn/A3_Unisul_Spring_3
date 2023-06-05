package com.example.demo.repository;

import com.example.demo.model.Midia;
import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Serializable> {
}
