package com.example.demo.generic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;

public interface RestServiceDTOInterface<Model, DTO> {

    Page<DTO> findAll(Pageable pageable);
    DTO get(Long id);

    DTO save(Model resource);

    DTO update(Model resource, Long id,String... ignoredProperties);

    DTO delete(Long id);

    DTO toDTO(Model resource);

    <T extends Object>Boolean exists(T condition);

    Link generateSelfLink(Long id, Class<RestControllerDTOInterface> controller);
}