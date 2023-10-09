package com.example.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.biblioteca.modelos.Juego;

@Repository
public interface IJuegoRepositorio extends JpaRepository<Juego, Long> {
    
}
