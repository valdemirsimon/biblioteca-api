package com.example.biblioteca.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.biblioteca.modelos.Comentario;
import com.example.biblioteca.servicios.ComentarioServicio;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/comentario")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio servicio;

    @Operation(summary = "Listar comentarios")
    @GetMapping(value = "todas")
    public ArrayList<Comentario> listar() {
        return this.servicio.listar();
    }

    @Operation(summary = "Obtener comentario")
    @GetMapping(value = "/{id}")
    public Optional<Comentario> obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id);
    }

    @Operation(summary = "Guradar comentario")
    @PostMapping
    public ResponseEntity<Comentario> guardar(@RequestBody Comentario opinion) {
        try {
            return ResponseEntity.status(201).body(this.servicio.guardar(opinion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }

    }

    @Operation(summary = "Actualizar comentario")
    @PutMapping
    public ResponseEntity<Comentario> actualizar(@RequestBody Comentario opinion) {
        try {
            return ResponseEntity.ok(this.servicio.actualizar(opinion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

     @Operation(summary = "Eliminar comentario")
    @DeleteMapping(path = "{id}")
    public String eliminar(@PathVariable Long id) {
        boolean ok = this.servicio.eliminar(id);
        if (ok) {
            return "Comentario eliminado";
        }
        return "Comentario no eliminado";
    }

}
