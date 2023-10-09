package com.example.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.modelos.Libro;

@Repository
public interface ILibroRepositorio extends JpaRepository<Libro, Long> {
    
}
