package com.example.biblioteca.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.biblioteca.modelos.Biografia;
import com.example.biblioteca.modelos.FileUploadResponse;
import com.example.biblioteca.servicios.BiografiaServicio;
import com.example.biblioteca.servicios.FileService;

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
@RequestMapping("/biografia")
public class BiografiaControlador {

    @Value("${host.url}")
    private String host;

    @Value("${server.port}")
    private String port;
    private String subPath = "biografia";

    @Autowired
    private BiografiaServicio biografiaServicio;
    @Autowired
    private FileService fileService;

    @Operation(summary = "Listar biografías")
    @GetMapping(value = "todos")
    public ArrayList<Biografia> listar() {
        return this.biografiaServicio.listar();
    }

    @Operation(summary = "Obtener biografía")
    @GetMapping(value = "/{id}")
    public Optional<Biografia> obtenerPorId(@PathVariable Long id) {
        return biografiaServicio.obtenerPorId(id);
    }

    @Operation(summary = "Guardar biografía")
    @PostMapping
    public ResponseEntity<Biografia> guardar(@RequestBody Biografia biografia) {
        try {
            return ResponseEntity.status(201).body(this.biografiaServicio.guardar(biografia));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }

    }

    @Operation(summary = "Guardar biografía mediante formulario form data")
    @PostMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Biografia> guardarFormData(@RequestParam("avatar") MultipartFile tapa,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("bio") String bio) {

        Biografia l = new Biografia();

        try {
            FileUploadResponse fl = fileService.uploadFile(tapa, subPath);
            String url = String.format("%s:%s/biografia/avatar/%s", host, port, fl.getFilename());
            l.setAvatar(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }

        l.setNombre(nombre);
        l.setApellido(apellido);
        l.setBio(bio);
        return ResponseEntity.ok(this.biografiaServicio.guardar(l));
    }

    @Operation(summary = "Actualizar biografía")
    @PutMapping
    public ResponseEntity<Biografia> actualizar(@RequestBody Biografia biografia) {
        try {
            return ResponseEntity.ok(this.biografiaServicio.actualizar(biografia));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Actualizar biografía mediante formulario form data")
    @PutMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Biografia> actualizarFormData(@RequestParam("avatar") MultipartFile tapa,
            @RequestParam("nombre") String nombre,
            @RequestParam("id") Long id,
            @RequestParam("apellido") String apellido,
            @RequestParam("bio") String bio) {

        Biografia l = new Biografia();
        try {
            FileUploadResponse fl = fileService.uploadFile(tapa, subPath);
            String url = String.format("%s:%s/biografia/avatar/%s", host, port, fl.getFilename());
            l.setAvatar(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
        l.setId(id);
        l.setNombre(nombre);
        l.setApellido(apellido);
        l.setBio(bio);
        return ResponseEntity.ok(this.biografiaServicio.actualizar(l));
    }

    @Operation(summary = "Eliminar biografía")
    @DeleteMapping(path = "{id}")
    public String eliminar(@PathVariable Long id) {
        boolean ok = this.biografiaServicio.eliminar(id);
        if (ok) {
            return "Biografía eliminada";
        }
        return "Biografía no eliminada";
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

    @Operation(summary = "Obtener avatar biografía")
    @GetMapping("/avatar/{filename}")
    public ResponseEntity<?> downloadCapa(@PathVariable String filename) {
        var fileBytes = fileService.getFile(subPath + "/" + filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileBytes);
    }

}
