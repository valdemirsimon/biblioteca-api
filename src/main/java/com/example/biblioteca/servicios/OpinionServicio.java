package com.example.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.modelos.Opinion;
import com.example.biblioteca.repositorios.IOpinionRepositorio;

@Service
public class OpinionServicio {
    @Autowired
    IOpinionRepositorio repositorio;

    public ArrayList<Opinion> listar() {
        return (ArrayList<Opinion>) repositorio.findAll();
    }

    public Opinion guardar(Opinion opinion) {
        return this.repositorio.save(opinion);
    }

    public Optional<Opinion> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public Opinion actualizar(Opinion opinion) {
        Opinion r = repositorio.findById(opinion.getId()).get();
        r.setNombre(opinion.getNombre());
        r.setOpinion(opinion.getOpinion());
        r.setAnonimo(opinion.getAnonimo());
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
