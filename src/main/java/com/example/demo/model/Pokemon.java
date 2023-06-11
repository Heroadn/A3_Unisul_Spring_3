package com.example.demo.model;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name ="pokemon")
public class Pokemon extends BaseModel {
    @Column(unique = true, nullable=false)
    private String nome;
    @Column(nullable=true)
    private String descricao;
    @CreationTimestamp
    private LocalDateTime data_criacao;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "atributo_id")
    private PokemonAtributo pokemonAtributo;

    @JsonIgnore
    @OneToMany(mappedBy = "pokemon")
    private Collection<MidiaPokemon> images;

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

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public PokemonAtributo getPokemonAtributo() {
        return pokemonAtributo;
    }

    public void setPokemonAtributo(PokemonAtributo pokemonAtributo) {
        this.pokemonAtributo = pokemonAtributo;
    }

    public Collection<MidiaPokemon> getImages() {
        return images;
    }

    public void setImages(Collection<MidiaPokemon> images) {
        this.images = images;
    }

    public static List<String> decodeTipos(String tipos)
    {
        List<String> result = new ArrayList<>();
        for (String tipo: tipos.split("\\|")) {
            if(!tipo.equals("|") && !tipo.equals(""))
                result.add(tipo);
        }

        return result;
    }

    public static String encodeTipos(String[] tipos)
    {
        String result = "";

        for (String tipo: tipos) {
            result = result.concat(tipo + "|");
        }

        return result;
    }
}
