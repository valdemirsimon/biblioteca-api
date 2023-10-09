package com.example.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.modelos.Comentario;
import com.example.biblioteca.repositorios.IComentarioRepositorio;


@Service
public class ComentarioServicio {
    @Autowired
    IComentarioRepositorio repositorio;

    public ArrayList<Comentario> listar() {
        return (ArrayList<Comentario>) repositorio.findAll();
    }

    public Comentario guardar(Comentario comentario) {
        return this.repositorio.save(comentario);
    }

    public Optional<Comentario> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public Comentario actualizar(Comentario opinion) {
        Comentario r = repositorio.findById(opinion.getId()).get();
        r.setComentario(opinion.getComentario());
        r.setBiografia(opinion.getBiografia());
        
        repositorio.save(r);
        return r;
    }

    public Boolean eliminar(Long id)  {
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
  }

}
