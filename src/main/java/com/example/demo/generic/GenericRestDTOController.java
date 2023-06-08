package com.example.demo.generic;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.model.BaseModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

abstract public class GenericRestDTOController
        <Model extends BaseModel,
         DTO extends BaseModel,
         Repository extends JpaRepository,
         Service extends RestServiceDTOInterface<Model, DTO>>
        implements RestControllerDTOInterface<Model, DTO>
{
    @Autowired
    public Repository repository;

    @Autowired
    public Service service;

    @Autowired
    public ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<Page<DTO>> findAll(Pageable pageable) {
        Page<DTO> list = service.findAll(pageable);
        list.forEach(model -> {
            model.add(linkTo(methodOn(this.getClass()).findById(model.ID)).withSelfRel());
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<DTO> findById(@PathVariable long id) {
        DTO model = service.get(id);
        model.add(linkTo(methodOn(this.getClass()).findById(model.ID)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @Override
    @PostMapping
    public ResponseEntity<DTO> save(Model model, HttpServletResponse response) {
        DTO fromDb = service.save(model);

        //Caso o usuario ja exista é provavel q save retorne null
        if(fromDb == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        publisher.publishEvent(new RecursoCriadoEvento(this, response, fromDb.getID()));
        return ResponseEntity.status(HttpStatus.CREATED).body(fromDb);
    }

    @Override
    @PutMapping(value = "/{id}")
    public ResponseEntity<DTO> update(@PathVariable long id, Model model, HttpServletResponse response, HttpServletRequest request) {
        DTO updated = service.update(model,id, new String[]{}, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<DTO> delete(@PathVariable long id, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.delete(id, request));
    }

}