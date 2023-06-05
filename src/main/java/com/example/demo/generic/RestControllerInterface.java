package com.example.demo.generic;

import com.example.demo.model.BaseModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface RestControllerInterface<Model extends BaseModel> {

    ResponseEntity<Model> findById(@PathVariable long id);

    ResponseEntity<Model> save(@RequestBody Model model, HttpServletResponse response, HttpServletRequest request);

    ResponseEntity<Model> update(@PathVariable final long id, @RequestBody Model model);

    ResponseEntity<Model> delete(@PathVariable final long id);
}