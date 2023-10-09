package com.example.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.biblioteca.modelos.Opinion;

@Repository
public interface IOpinionRepositorio extends JpaRepository<Opinion, Long> {
    
}
