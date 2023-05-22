package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class UsuarioDTO {
    private Long ID;

    private String nome;

    private String email;

    private String descricao;

    private LocalDateTime data_criacao;

    private Collection<Link> images;

    public UsuarioDTO(Usuario usuario) {
        this.ID = usuario.getID();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.descricao = usuario.getDescricao();
        this.data_criacao = usuario.getData_criacao();
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public Collection<Link> getImages() {
        return images;
    }

    public void setImages(Collection<Link> images) {
        this.images = images;
    }


}
