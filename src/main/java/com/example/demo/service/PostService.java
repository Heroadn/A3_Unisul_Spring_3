package com.example.demo.service;

import com.example.demo.generic.BasicRestService;
import com.example.demo.model.Midia;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Post;
import com.example.demo.model.Usuario;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.repository.MidiaUsuarioRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.resources.MidiaController;
import com.example.demo.utils.Bruxaria;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import static com.example.demo.utils.Bruxaria.getAbsoluteImagePath;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PostService extends BasicRestService<Post, PostRepository> {

    @Autowired
    PostRepository postRepository;

    @Override
    public <T extends Object> Boolean exists(T fileName) {
        return false;
    }

    public Page<Post> findAllPostsByUsuarioID(Long id, Pageable pageable)
    {
        return postRepository.findAllByUsuarioID(id, pageable);
    }
}
