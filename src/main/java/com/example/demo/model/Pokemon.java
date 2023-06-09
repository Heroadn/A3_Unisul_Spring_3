package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name ="pokemon")
public class Pokemon extends BaseModel {
    @Column(unique = true, nullable=false)
    private String nome;
    @Column(nullable=true)
    private String descricao;
    @CreationTimestamp
    private LocalDateTime data_criacao;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pokemon")
    private PokemonAtributo pokemonAtributo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PokemonAtributo getPokemonAtributo() {
        return pokemonAtributo;
    }

    public void setPokemonAtributo(PokemonAtributo pokemonAtributo) {
        this.pokemonAtributo = pokemonAtributo;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }
}
