package com.example.demo.resources;

import com.example.demo.utils.Bruxaria;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;

class Media
{
    public String file_name;
    public String file_size;

    //transient
    public String file_image64;
}


//controller responsavel por salvar imagens
@RestController
@RequestMapping(path = "/media", produces = "application/hal+json")
public class MediaController {
    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> get(@PathVariable(name = "name") String name) throws IOException {
        var imgFile = new ClassPathResource("image/" + name);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    @PostMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Link> post(@RequestBody Media media) {
        //TODO: mover para mediaService
        String base64 = media.file_image64;
        String base64Image = Bruxaria.getBase64Image(base64);
        String ext = Bruxaria.getBase64Ext(base64);
        String url = "media/sid";

        String outPath = Bruxaria.getAbsoluteImagePath()
                + Bruxaria.toImageFilename(media.file_name, ext);
        Bruxaria.writeBase64ImageToPath(base64Image, outPath);

        Link link = WebMvcLinkBuilder.linkTo(MediaController.class)
                .withRel("/" + media.file_name);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(link);
    }
}