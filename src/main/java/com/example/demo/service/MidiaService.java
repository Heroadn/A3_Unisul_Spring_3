package com.example.demo.service;

import com.example.demo.generic.BasicRestService;
import com.example.demo.model.Midia;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.repository.MidiaUsuarioRepository;
import com.example.demo.resources.MidiaController;
import com.example.demo.utils.Bruxaria;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MidiaService extends BasicRestService<Midia, MidiaRepository> {

    @Autowired
    MidiaRepository mediaRepository;

    @Autowired
    private MidiaUsuarioRepository midiaUsuarioRepository;

    @Override
    public Midia save(Midia midia)
    {
        if(exists(midia.getFileName()))
            return null;
        return super.save(midia);
    }

    @Override
    public <T extends Object> Boolean exists(T fileName) {
        return mediaRepository.existsByFileName((String) fileName);
    }

    //retorna um link para o recurso "midia"
    public Link toLink(Midia midia)
    {
        Link link = null;
        try {
            link = linkTo(methodOn(MidiaController.class)
                    .getImage(midia.getFileName())).withSelfRel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return link;
    }

    public void saveMidiaUsuario(Usuario usuario, Midia midia) {
        MidiaUsuario midiaUsuario = new MidiaUsuario();
        midiaUsuario.setMidia(midia);
        midiaUsuario.setUsuario(usuario);
        midiaUsuarioRepository.save(midiaUsuario);
    }

    public Midia getByFileName(String fileName) {
        Optional<Midia> resource = mediaRepository.findByFileName((fileName));
        resource.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return resource.get();
    }

    //loads image and create a response entity as IMAGE_JPEG
    public ResponseEntity<InputStreamResource> createResponseImage(String name){
        var imgFile = new ClassPathResource("image/" + name);
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createUUID(Midia midia)
    {
        byte[] bytes = Base64.decodeBase64(midia.getFileImage64());

        String midiaFileName = midia.getFileName() + "_"
                + UUID.nameUUIDFromBytes(bytes).toString();
        return midiaFileName;
    }

    public void createImage(Midia midia) {
        String base64 = midia.getFileImage64();
        String base64Image = Bruxaria.getBase64Image(base64);
        String ext = Bruxaria.getBase64Ext(base64);

        //monta o fileName + ext, para o diretorio de imagens
        String outPath = Bruxaria.getAbsoluteImagePath()
                + Bruxaria.toImageFilename(midia.getFileName(), ext);
        Bruxaria.writeBase64ImageToPath(base64Image, outPath);
    }
}
