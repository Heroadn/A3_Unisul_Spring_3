package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name ="comentario")
public class Comentario extends BaseModel {
    @Column(unique = true, nullable=false)
    private String titulo;

    @Column(unique=true, nullable=false)
    private String texto;

    @CreationTimestamp
    private LocalDateTime data_comentario;

    @JoinColumn(name = "id_usuario")
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @JsonIgnore
    @JoinColumn(name = "id_post")
    @OneToOne(cascade = CascadeType.ALL)
    private Post post;

    public Comentario() {}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData_comentario() {
        return data_comentario;
    }

    public void setData_comentario(LocalDateTime data_comentario) {
        this.data_comentario = data_comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
