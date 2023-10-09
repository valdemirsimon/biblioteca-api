package com.example.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.biblioteca.modelos.Recomendacion;
import com.example.biblioteca.repositorios.IRecomendacionRepositorio;

@Service
public class RecomendacionServicio {
    @Autowired
    IRecomendacionRepositorio recomendacionRepositorio;

    public ArrayList<Recomendacion> listar() {
        return (ArrayList<Recomendacion>) recomendacionRepositorio.findAll();
    }

    public Recomendacion guardar(Recomendacion recomendacion) {
        return this.recomendacionRepositorio.save(recomendacion);
    }

    public Optional<Recomendacion> obtenerPorId(Long id) {
        return recomendacionRepositorio.findById(id);
    }

    public Recomendacion actualizar(Recomendacion recomendacion) {
        Recomendacion r = recomendacionRepositorio.findById(recomendacion.getId()).get();
        r.setTitulo(recomendacion.getTitulo());
        r.setAutor(recomendacion.getAutor());
        r.setNumeroPaginas(recomendacion.getNumeroPaginas());
        r.setTapa(recomendacion.getTapa());
        r.setSinopsis(recomendacion.getSinopsis());
        recomendacionRepositorio.save(r);
        return r;
    }

    public Boolean eliminar(Long id)  {
        try {
            recomendacionRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
  }

}
