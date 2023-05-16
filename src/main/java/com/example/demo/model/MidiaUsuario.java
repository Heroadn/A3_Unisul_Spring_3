package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name ="midia_usuario")
public class MidiaUsuario extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "midia_id")
    private Midia midia;

    @CreationTimestamp
    private LocalDate data_alteracao;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Midia getMidia() {
        return midia;
    }

    public void setMidia(Midia midia) {
        this.midia = midia;
    }

    public LocalDate getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(LocalDate data_alteracao) {
        this.data_alteracao = data_alteracao;
    }
}
