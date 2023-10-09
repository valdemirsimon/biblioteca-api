package com.example.biblioteca.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.biblioteca.modelos.Opinion;
import com.example.biblioteca.servicios.OpinionServicio;

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
@RequestMapping("/opinion")
public class OpinionControlador {

    @Autowired
    private OpinionServicio servicio;

    @Operation(summary = "Listar opiniones")
    @GetMapping(value = "todas")
    public ArrayList<Opinion> listar() {
        return this.servicio.listar();
    }

    @Operation(summary = "Obtener opinión")
    @GetMapping(value = "/{id}")
    public Optional<Opinion> obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id);
    }

    @Operation(summary = "Guardar opinión")
    @PostMapping
    public ResponseEntity<Opinion> guardar(@RequestBody Opinion opinion) {
        try {
            return ResponseEntity.status(201).body(this.servicio.guardar(opinion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }

    }

    @Operation(summary = "Actualizar opinión")
    @PutMapping
    public ResponseEntity<Opinion> actualizar(@RequestBody Opinion opinion) {
        try {
            return ResponseEntity.ok(this.servicio.actualizar(opinion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Eliminar opinión")
    @DeleteMapping(path = "{id}")
    public String eliminar(@PathVariable Long id) {
        boolean ok = this.servicio.eliminar(id);
        if (ok) {
            return "Opinión eliminada";
        }
        return "Opinión no eliminada";
    }

}
