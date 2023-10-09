package com.example.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.modelos.Juego;
import com.example.biblioteca.repositorios.IJuegoRepositorio;

@Service
public class JuegoServicio {
    @Autowired
    IJuegoRepositorio repositorio;

    public ArrayList<Juego> listar() {
        return (ArrayList<Juego>) repositorio.findAll();
    }

    public Juego guardar(Juego juego) {
        return this.repositorio.save(juego);
    }

    public Optional<Juego> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public Juego actualizar(Juego juego) {
        Juego j = repositorio.findById(juego.getId()).get();

        j.setNombre(juego.getNombre());
        j.setDescripcion(juego.getDescripcion());
        j.setAvatar(juego.getAvatar());
        j.setUrl(juego.getUrl());

        repositorio.save(j);
        return j;
    }

    public Boolean eliminar(Long id) {
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
