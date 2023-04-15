package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@MappedSuperclass
public class BaseModel extends RepresentationModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT", unique = true)
    public long ID;

    @Transient
    @JsonIgnore
    private String[] ignored;

    @JsonProperty("ID")
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String[] getIgnored() {
        return ignored;
    }

    public void setIgnored(String... ignored) {
        this.ignored = ignored;
    }
}
