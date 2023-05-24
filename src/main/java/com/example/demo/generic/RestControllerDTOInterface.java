package com.example.demo.generic;

import com.example.demo.model.BaseModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface RestControllerDTOInterface<
        Model extends BaseModel,
        DTO extends BaseModel> {

    ResponseEntity<DTO> findById(@PathVariable long id);

    ResponseEntity<DTO> save(@RequestBody Model model, HttpServletResponse response);

    ResponseEntity<DTO> update(@PathVariable final long id, @RequestBody Model model);

    ResponseEntity<DTO> delete(@PathVariable final long id);
}