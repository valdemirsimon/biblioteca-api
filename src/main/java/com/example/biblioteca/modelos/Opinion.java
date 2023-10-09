package com.example.biblioteca.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "opinion")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",length = 80) // opcional, podemos aplicar reglas aqui
    private String nombre;
    @Column(name = "opinion", length = 80,nullable = false)
    private String opinion;
    @Column(name = "anonimo")
    private Boolean anonimo;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getOpinion() {
        return opinion;
    }
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
    public Boolean getAnonimo() {
        return anonimo;
    }
    public void setAnonimo(Boolean anonimo) {
        this.anonimo = anonimo;
    }

   
}
