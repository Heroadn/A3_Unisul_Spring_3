package com.example.demo.service;

import com.example.demo.generic.BasicRestService;
import com.example.demo.model.Midia;
import com.example.demo.model.MidiaUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.repository.MidiaRepository;
import com.example.demo.repository.MidiaUsuarioRepository;
import com.example.demo.utils.Bruxaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class MidiaService extends BasicRestService<Midia, MidiaRepository> {

    @Autowired
    MidiaRepository mediaRepository;

    @Autowired
    private MidiaUsuarioRepository midiaUsuarioRepository;

    @Override
    public Midia save(Midia midia) {
        if(exists(midia.getFileName()))
            return null;
        return super.save(midia);
    }

    public void saveMediaUsuario(Usuario usuario, Midia midia) {
        MidiaUsuario midiaUsuario = new MidiaUsuario();
        midiaUsuario.setMidia(midia);
        midiaUsuario.setUsuario(usuario);
        midiaUsuarioRepository.save(midiaUsuario);
    }

    @Override
    public <T extends Object> Boolean exists(T fileName) {
        return mediaRepository.existsByFileName((String) fileName);
    }

    public Midia getByFileName(String fileName) {
        Optional<Midia> resource = mediaRepository.findByFileName((fileName));
        resource.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return resource.get();
    }

    //loads image and create a response entity as IMAGE_JPEG
    public ResponseEntity<InputStreamResource> createResponseImage(String name){
        //TODO: adicionar checagem se a imagem existe
        var imgFile = new ClassPathResource("image/" + name);
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
