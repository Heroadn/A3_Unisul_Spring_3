package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name ="midia")
public class Midia extends BaseModel {
    @Column(name="file_name",unique = true, nullable=false)
    private String fileName;

    @Column(name="file_size", nullable=false)
    private String fileSize;

    @CreationTimestamp
    @Column(name="data_insercao")
    private LocalDateTime dataInsercao;

    @OneToMany(mappedBy = "midia")
    private Set<MidiaUsuario> images;

    //used to store image
    @Transient
    private String fileImage64;

    public Midia() {}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(LocalDateTime dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public String getFileImage64() {
        return fileImage64;
    }

    public void setFileImage64(String fileImage64) {
        this.fileImage64 = fileImage64;
    }

    public Set<MidiaUsuario> getImages() {
        return images;
    }

    public void setImages(Set<MidiaUsuario> images) {
        this.images = images;
    }

    public void addMidiaUsuario(MidiaUsuario midiaUsuario)
    {
        images.add(midiaUsuario);
    }
}
