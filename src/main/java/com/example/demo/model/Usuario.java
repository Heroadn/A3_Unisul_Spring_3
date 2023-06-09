package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="usuario")
public class Usuario extends BaseModel {
    @Column(unique = true, nullable=false)
    private String nome;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false, length = 255)
    private String descricao;

    @Column(nullable=false)
    private String token;

    @CreationTimestamp
    private LocalDateTime data_criacao;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Collection<MidiaUsuario> images;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private Collection<Post> posts;

    //password is stored into authorization server
    //field purpose is just to receive when post request
    @Transient
    private String senha;

    public Usuario() {}

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getData_criacao() { return data_criacao; }

    public void setData_criacao(LocalDateTime data_criacao) { this.data_criacao = data_criacao; }

    public Collection<MidiaUsuario> getImages() {
        return images;
    }

    public void setImages(Collection<MidiaUsuario> images) {
        this.images = images;
    }

    public void addMidiaUsuario(MidiaUsuario midiaUsuario)
    {
        images.add(midiaUsuario);
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }
}
