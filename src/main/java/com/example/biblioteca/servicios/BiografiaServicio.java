package com.example.biblioteca.servicios;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.modelos.Biografia;
import com.example.biblioteca.repositorios.IBiografiaRepositorio;


@Service
public class BiografiaServicio {
    @Autowired
    IBiografiaRepositorio biografiaRepositorio;

    public ArrayList<Biografia> listar() {
        return (ArrayList<Biografia>) biografiaRepositorio.findAll();
    }

    public Biografia guardar(Biografia biografia) {
        return this.biografiaRepositorio.save(biografia);
    }

    public Optional<Biografia> obtenerPorId(Long id) {
        return biografiaRepositorio.findById(id);
    }

    public Biografia actualizar(Biografia biografia) {
        Biografia l = biografiaRepositorio.findById(biografia.getId()).get();
    
        l.setNombre(biografia.getNombre());
        l.setApellido(biografia.getApellido());
        l.setAvatar(biografia.getAvatar());
        l.setBio(biografia.getBio());
        biografiaRepositorio.save(l);
        return l;
    }

    public Boolean eliminar(Long id) {
        try {
            biografiaRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
  }

}
