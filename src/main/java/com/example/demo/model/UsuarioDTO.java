package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class UsuarioDTO extends BaseModel{
    private String nome;

    private String email;

    private String descricao;

    private LocalDateTime data_criacao;

    private Collection<String> images;

    private Collection<Post> posts;

    public UsuarioDTO(Usuario usuario) {
        this.ID = usuario.getID();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.descricao = usuario.getDescricao();
        this.data_criacao = usuario.getData_criacao();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Collection<String> getImages() {
        return images;
    }

    public void setImages(Collection<String> images) {
        this.images = images;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }
}
