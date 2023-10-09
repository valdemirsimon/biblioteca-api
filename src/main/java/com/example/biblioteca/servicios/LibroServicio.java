package com.example.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.biblioteca.modelos.Libro;
import com.example.biblioteca.repositorios.ILibroRepositorio;

@Service
public class LibroServicio {
    @Autowired
    ILibroRepositorio libroRepositorio;

    public ArrayList<Libro> listarLibros() {
        return (ArrayList<Libro>) libroRepositorio.findAll();
    }

    public Libro guardar(Libro libro) {
        return this.libroRepositorio.save(libro);
    }

    public Optional<Libro> obtenerPorId(Long id) {
        return libroRepositorio.findById(id);
    }

    public Libro actualizar(Libro libro) {
        Libro l = libroRepositorio.findById(libro.getId()).get();
        l.setTitulo(libro.getTitulo());
        l.setAutor(libro.getAutor());
        l.setNumeroPaginas(libro.getNumeroPaginas());
        l.setTapa(libro.getTapa());
        l.setContenido(libro.getContenido());
        l.setSinopsis(libro.getSinopsis());
        libroRepositorio.save(l);
        return l;
    }

    public Boolean eliminar(Long id) {
        try {
            libroRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
  }

}
