package com.example.demo.model;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name ="post")
public class Post extends BaseModel {
    @Column(unique = true, nullable=false)
    private String titulo;

    @Column(unique=true, nullable=false)
    private String texto;

    @CreationTimestamp
    private LocalDateTime data_post;

    @JoinColumn(name = "id_usuario")
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

    public Usuario getUsuario() {
        return usuario;
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
