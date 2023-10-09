package com.example.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.biblioteca.modelos.Recomendacion;

@Repository
public interface IRecomendacionRepositorio extends JpaRepository<Recomendacion, Long> {
    
}
