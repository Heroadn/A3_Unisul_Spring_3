package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name ="pokemon_atributo")
public class PokemonAtributo extends BaseModel{
    @Column(unique = true, nullable=false)
    private String sexo;
    @Column(unique = true, nullable=false)
    private String tipo;
    @Column(unique = true, nullable=false)
    private String geracao;
    @Column(unique = true, nullable=false)
    private Long ataqueMinimo;
    @Column(unique = true, nullable=false)
    private Long ataqueMaximo;
    @Column(unique = true, nullable=false)
    private Long defesaMinimo;
    @Column(unique = true, nullable=false)
    private Long defesaMaximo;

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
}
