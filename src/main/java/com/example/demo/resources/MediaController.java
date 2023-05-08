package com.example.demo.resources;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.generic.GenericRestController;
import com.example.demo.model.Midia;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.service.MidiaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


//controller responsavel por salvar imagens
@RestController
@RequestMapping(path = "/midia", produces = "application/hal+json")
public class MediaController extends GenericRestController<Midia, MidiaRepository, MidiaService> {

    //TODO: mandar mensagem de erro caso imagem com mesmo nome ja exista
    @Override
    @PostMapping
    public ResponseEntity<Midia> save(Midia midia, HttpServletResponse response) {
        Midia fromDb = service.save(midia);
        if(fromDb == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        //TODO: check for more errors before attempting to store image
        service.storeImage(midia);
        publisher.publishEvent(new RecursoCriadoEvento(this, response, fromDb.getID()));
        return ResponseEntity.status(HttpStatus.CREATED).body(fromDb);
    }

    @GetMapping(value = "/getImage/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable(name = "name") String name) throws IOException {
        ResponseEntity response = service.createResponseImage(name);
        return response;
    }
}