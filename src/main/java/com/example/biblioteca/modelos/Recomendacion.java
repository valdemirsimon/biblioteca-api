package com.example.biblioteca.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recomendacion")
public class Recomendacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo",length = 80,nullable = false) // opcional, podemos aplicar reglas aqui
    private String titulo;
    @Column(name = "autor", length = 80,nullable = false)
    private String autor;
    @Column
    private Integer numeroPaginas;
    @Column(name = "sinopsis", length = 500)
    private String sinopsis;
    @Column
    private String tapa; // Url de la imagen
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTapa() {
        return tapa;
    }

    public void setTapa(String tapa) {
        this.tapa = tapa;
    }
}
