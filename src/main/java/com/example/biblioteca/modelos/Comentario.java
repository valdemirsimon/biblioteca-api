package com.example.biblioteca.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comentario",length = 500) // opcional, podemos aplicar reglas aqui
    private String comentario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "biografia_id")
    @JsonBackReference
    private Biografia biografia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Biografia getBiografia() {
        return biografia;
    }

    public void setBiografia(Biografia biografia) {
        this.biografia = biografia;
    }
   
}
