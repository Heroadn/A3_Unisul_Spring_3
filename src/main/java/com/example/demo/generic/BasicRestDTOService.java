package com.example.demo.generic;

import com.example.demo.model.BaseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

abstract public class BasicRestDTOService<
        Model extends BaseModel,
        DTO extends BaseModel,
        Repository extends JpaRepository>
        implements RestServiceDTOInterface<Model, DTO> {

    @Autowired
    Repository repo;

    @Override
    public Page<DTO> findAll(Pageable pageable)
    {
        List<Model> list = repo.findAll(pageable).getContent();
        List<DTO> listDTO = new ArrayList<>();

        //converting Model to DTO
        list.forEach(model -> {
            System.out.println(";)");
            listDTO.add(this.toDTO(model));
        });

        return new PageImpl<>(listDTO, pageable, list.size());
    }


    @Override
    public DTO get(Long id) {
        Optional<Model> resource = repo.findById(id);
        resource.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return toDTO(resource.get());
    }

    @Override
    public DTO save(Model resource) {
        return toDTO((Model)repo.save(resource));
    }

    @Override
    public DTO update(Model resource, Long id,String... ignoredProperties) {
        Optional<Model> fromDb = repo.findById(id);
        fromDb.orElseThrow(() -> new EmptyResultDataAccessException(1));

        if(ignoredProperties.length == 0) {
            BeanUtils.copyProperties(resource, fromDb, resource.getIgnored());
        }

        return toDTO((Model)repo.save(fromDb));
    }

    @Override
    public DTO delete(Long id) {
        Optional<Model> fromDb = repo.findById(id);
        fromDb.orElseThrow(() -> new EmptyResultDataAccessException(1));

        DTO dto = toDTO(fromDb.get());
        repo.delete(fromDb);
        return dto;
    }

    @Override
    public <T extends Object> Boolean exists(T condition) {
        return false;
    }

    @Override
    @Deprecated
    public Link generateSelfLink(Long id, Class<RestControllerDTOInterface> controller){
        return linkTo(methodOn(controller).findById(id)).withSelfRel();
    }

}