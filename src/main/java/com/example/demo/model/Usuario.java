package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name ="usuario")
public class Usuario extends BaseModel {
    @Column(unique = true, nullable=false)
    private String nome;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String descricao;

    @Column(nullable=false)
    private String token;

    @CreationTimestamp
    private LocalDate data_criacao;

    //password is stored into authorization server
    //field purpose is just to receive when post request
    @Transient
    private String senha;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
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

    public LocalDate getData_criacao() { return data_criacao; }

    public void setData_criacao(LocalDate data_criacao) { this.data_criacao = data_criacao; }
}
