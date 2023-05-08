package com.example.demo.generic;

import com.example.demo.event.RecursoCriadoEvento;
import com.example.demo.model.BaseModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

abstract public class GenericRestController
        <Model extends BaseModel,
         Repository extends JpaRepository,
         Service extends RestServiceInterface<Model>>
        implements RestControllerInterface<Model>
{
    @Autowired
    public Repository repository;

    @Autowired
    public Service service;

    @Autowired
    public ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<Page<Model>> findAll(Pageable pageable) {
        Page<Model> list = repository.findAll(pageable);
        list.forEach(model -> {
            model.add(linkTo(methodOn(this.getClass()).findById(model.ID)).withSelfRel());
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Model> findById(@PathVariable long id) {
        Model model = service.get(id);
        model.add(linkTo(methodOn(this.getClass()).findById(model.ID)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @Override
    @PostMapping
    public ResponseEntity<Model> save(Model model, HttpServletResponse response) {
        Model fromDb = service.save(model);

        //Caso o usuario ja exista é provavel q save retorne null
        if(fromDb == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        publisher.publishEvent(new RecursoCriadoEvento(this, response, fromDb.getID()));
        return ResponseEntity.status(HttpStatus.CREATED).body(fromDb);
    }

    @Override
    @PutMapping(value = "/{id}")
    public ResponseEntity<Model> update(@PathVariable long id, Model model) {
        Model updated = service.update(model,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Model> delete(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.delete(id));
    }

}