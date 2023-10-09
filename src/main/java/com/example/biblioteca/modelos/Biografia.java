package com.example.biblioteca.modelos;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "biografia")
public class Biografia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",length = 80,nullable = false) // opcional, podemos aplicar reglas aqui
    private String nombre;

    @Column(name = "apellido",length = 80,nullable = false) // opcional, podemos aplicar reglas aqui
    private String apellido;

    @Column(name = "bio", length = 500)
    private String bio;
    @Column
    private String avatar; // Url del avatar
   @OneToMany(mappedBy = "biografia", fetch = FetchType.EAGER)
   @JsonManagedReference
   private java.util.List<Comentario> comentarios = new ArrayList<>();

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
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public java.util.List<Comentario> getComentarios() {
        return comentarios;
    }
    public void setComentarios(java.util.List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }




}
