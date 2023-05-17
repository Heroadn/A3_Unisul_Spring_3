package com.example.demo.generic;

import org.springframework.hateoas.Link;
import org.springframework.security.core.Authentication;

public interface RestServiceInterface<Model> {

    Model get(Long id);

    Model save(Model resource);

    Model update(Model resource, Long id,String... ignoredProperties);

    Model delete(Long id);

    <T extends Object>Boolean exists(T condition);

    Link generateSelfLink(Long id, Class<RestControllerInterface> controller);
}