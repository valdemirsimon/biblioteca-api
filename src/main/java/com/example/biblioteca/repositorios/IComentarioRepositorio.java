package com.example.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.biblioteca.modelos.Comentario;

@Repository
public interface IComentarioRepositorio extends JpaRepository<Comentario, Long> {
    
}
