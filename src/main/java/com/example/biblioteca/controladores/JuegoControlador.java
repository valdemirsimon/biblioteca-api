package com.example.biblioteca.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.biblioteca.modelos.FileUploadResponse;
import com.example.biblioteca.modelos.Juego;
import com.example.biblioteca.servicios.FileService;
import com.example.biblioteca.servicios.JuegoServicio;

import io.swagger.v3.oas.annotations.Operation;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/juego")
public class JuegoControlador {

    @Value("${host.url}")
    private String host;

    @Value("${server.port}")
    private String port;
    private String subPath = "juego";

    @Autowired
    private JuegoServicio servicio;
    @Autowired
    private FileService fileService;

    @Operation(summary = "Listar juegos")
    @GetMapping(value = "todos")
    public ArrayList<Juego> listar() {
        return this.servicio.listar();
    }

    @Operation(summary = "Obtener juego")
    @GetMapping(value = "/{id}")
    public Optional<Juego> obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id);
    }

    @Operation(summary = "Guardar juego")
    @PostMapping
    public ResponseEntity<Juego> guardar(@RequestBody Juego juego) {
        try {
            return ResponseEntity.status(201).body(this.servicio.guardar(juego));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }

    }

    @Operation(summary = "Guardar juego mediante formulario form data")
    @PostMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Juego> guardarFormData(@RequestParam("avatar") MultipartFile avatar,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("url") String url) {

        Juego j = new Juego();

        try {
            FileUploadResponse fl = fileService.uploadFile(avatar, subPath);
            String urlAvatar = String.format("%s:%s/juego/avatar/%s", host, port, fl.getFilename());
            j.setAvatar(urlAvatar);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }

        j.setNombre(nombre);
        j.setDescripcion(descripcion);
        j.setUrl(url);
        return ResponseEntity.ok(this.servicio.guardar(j));
    }

    @Operation(summary = "Actualizar juego")
    @PutMapping
    public ResponseEntity<Juego> actualizar(@RequestBody Juego juego) {
        try {
            return ResponseEntity.ok(this.servicio.actualizar(juego));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Actualizar juego mediante formulario form data")
    @PutMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Juego> actualizarFormData(@RequestParam("avatar") MultipartFile avatar,
            @RequestParam("nombre") String nombre,
            @RequestParam("id") Long id,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("url") String url) {

        Juego j = new Juego();
        try {
            FileUploadResponse fl = fileService.uploadFile(avatar, subPath);
            String urlAvatar = String.format("%s:%s/juego/avatar/%s", host, port, fl.getFilename());
            j.setAvatar(urlAvatar);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
        j.setId(id);
        j.setNombre(nombre);
        j.setDescripcion(descripcion);
        j.setUrl(url);
        return ResponseEntity.ok(this.servicio.actualizar(j));
    }

    @Operation(summary = "Eliminar juego")
    @DeleteMapping(path = "{id}")
    public String eliminar(@PathVariable Long id) {
        boolean ok = this.servicio.eliminar(id);
        if (ok) {
            return "Juego eliminado";
        }
        return "Juego no eliminado";
    }

    @Operation(summary = "Cargar archivo")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("avatar") MultipartFile file) {
        try {
            FileUploadResponse fileUploadResponse = fileService.uploadFile(file, subPath);
            return ResponseEntity.ok(fileUploadResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
    }

    @Operation(summary = "Obtener avatar del juego")
    @GetMapping("/avatar/{filename}")
    public ResponseEntity<?> downloadCapa(@PathVariable String filename) {
        var fileBytes = fileService.getFile(subPath + "/" + filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileBytes);
    }

}
