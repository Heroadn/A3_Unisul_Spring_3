package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name ="pokemon_atributo")
public class PokemonDTO extends BaseModel{
    private String nome;
    private String descricao;
    private String sexo;
    private String tipo;
    private String geracao;
    private Long ataqueMinimo;
    private Long ataqueMaximo;
    private Long defesaMinimo;
    private Long defesaMaximo;
    private Collection<String> images;

    public PokemonDTO(Pokemon pokemon) {
        PokemonAtributo atributo = pokemon.getPokemonAtributo();

        this.nome = pokemon.getNome();
        this.descricao = pokemon.getDescricao();
        this.sexo = atributo.getSexo();
        this.tipo = atributo.getTipo();
        this.geracao = atributo.getGeracao();
        this.ataqueMinimo = atributo.getAtaqueMinimo();
        this.ataqueMaximo = atributo.getAtaqueMaximo();
        this.defesaMinimo = atributo.getDefesaMinimo();
        this.defesaMaximo = atributo.getDefesaMaximo();
    }

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGeracao() {
        return geracao;
    }

    public void setGeracao(String geracao) {
        this.geracao = geracao;
    }

    public Long getAtaqueMinimo() {
        return ataqueMinimo;
    }

    public void setAtaqueMinimo(Long ataqueMinimo) {
        this.ataqueMinimo = ataqueMinimo;
    }

    public Long getAtaqueMaximo() {
        return ataqueMaximo;
    }

    public void setAtaqueMaximo(Long ataqueMaximo) {
        this.ataqueMaximo = ataqueMaximo;
    }

    public Long getDefesaMinimo() {
        return defesaMinimo;
    }

    public void setDefesaMinimo(Long defesaMinimo) {
        this.defesaMinimo = defesaMinimo;
    }

    public Long getDefesaMaximo() {
        return defesaMaximo;
    }

    public void setDefesaMaximo(Long defesaMaximo) {
        this.defesaMaximo = defesaMaximo;
    }

    public Collection<String> getImages() {
        return images;
    }

    public void setImages(Collection<String> images) {
        this.images = images;
    }
}
