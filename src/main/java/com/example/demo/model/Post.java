package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name ="post")
public class Post extends BaseModel {
    @Column(name="titulo", nullable=false)
    private String titulo;

    @Column(nullable=false)
    private String texto;

    @CreationTimestamp
    private LocalDateTime data_post;

    @JoinColumn(name = "usuario_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    Collection<Comentario> comentarios;

    public Post() {}

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

    public LocalDateTime getData_post() {
        return data_post;
    }

    public void setData_post(LocalDateTime data_post) {
        this.data_post = data_post;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Collection<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
