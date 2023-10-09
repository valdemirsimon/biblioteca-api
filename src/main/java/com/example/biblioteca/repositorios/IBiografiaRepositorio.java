package com.example.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.biblioteca.modelos.Biografia;

@Repository
public interface IBiografiaRepositorio extends JpaRepository<Biografia, Long> {
    
}
