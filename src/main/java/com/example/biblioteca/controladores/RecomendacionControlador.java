package com.example.biblioteca.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.biblioteca.modelos.FileUploadResponse;
import com.example.biblioteca.modelos.Recomendacion;
import com.example.biblioteca.servicios.FileService;
import com.example.biblioteca.servicios.RecomendacionServicio;

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
@RequestMapping("/recomendacion")
public class RecomendacionControlador {

    @Value("${host.url}")
    private String host;

    @Value("${server.port}")
    private String port;

    private String subPath = "recomendacion";

    @Autowired
    private RecomendacionServicio recomendacionServicio;
    @Autowired
    private FileService fileService;

    @Operation(summary = "Listar recomendaciones")
    @GetMapping(value = "todas")
    public ArrayList<Recomendacion> listar() {
        return this.recomendacionServicio.listar();
    }

    @Operation(summary = "Obtener recomendación")
    @GetMapping(value = "/{id}")
    public Optional<Recomendacion> obtenerPorId(@PathVariable Long id) {
        return recomendacionServicio.obtenerPorId(id);
    }

    @Operation(summary = "Guardar recomendación")
    @PostMapping
    public ResponseEntity<Recomendacion> guardar(@RequestBody Recomendacion recomendacion) {
        try {
            return ResponseEntity.status(201).body(this.recomendacionServicio.guardar(recomendacion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }

    }

    @Operation(summary = "Guardar recomendación mediante formulario form data")
    @PostMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Recomendacion> guardarFormData(
            @RequestParam("tapa") MultipartFile tapa,
            @RequestParam("autor") String autor,
            @RequestParam("titulo") String titulo,
            @RequestParam("numeroPaginas") Integer numeroPaginas,
            @RequestParam("sinopsis") String sinopsis) {

        Recomendacion r = new Recomendacion();

        try {
            FileUploadResponse fl = fileService.uploadFile(tapa, subPath);
            String url = String.format("%s:%s/recomendacion/tapa/%s", host, port, fl.getFilename());
            r.setTapa(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }

        r.setAutor(autor);
        r.setNumeroPaginas(numeroPaginas);
        r.setSinopsis(sinopsis);
        r.setTitulo(titulo);
        return ResponseEntity.ok(this.recomendacionServicio.guardar(r));
    }

    @Operation(summary = "Actualizar recomendación")
    @PutMapping
    public ResponseEntity<Recomendacion> actualizar(@RequestBody Recomendacion recomendacion) {
        try {
            return ResponseEntity.ok(this.recomendacionServicio.actualizar(recomendacion));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Actualizar recomendación mediante formulario form data")
    @PutMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Recomendacion> actualizarFormData(
            @RequestParam("tapa") MultipartFile tapa,
            @RequestParam("autor") String autor,
            @RequestParam("id") Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("numeroPaginas") Integer numeroPaginas,
            @RequestParam("sinopsis") String sinopsis) {

        Recomendacion r = new Recomendacion();

        try {
            FileUploadResponse fl = fileService.uploadFile(tapa, subPath);
            String url = String.format("%s:%s/recomendacion/tapa/%s", host, port, fl.getFilename());
            r.setTapa(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
        r.setId(id);
        r.setAutor(autor);
        r.setNumeroPaginas(numeroPaginas);
        r.setSinopsis(sinopsis);
        r.setTitulo(titulo);
        return ResponseEntity.ok(this.recomendacionServicio.actualizar(r));
    }

    @Operation(summary = "Eliminar recomendación")
    @DeleteMapping(path = "{id}")
    public String eliminar(@PathVariable Long id) {
        boolean ok = this.recomendacionServicio.eliminar(id);
        if (ok) {
            return "Recomendación eliminada";
        }
        return "Recomendación no eliminada";
    }

    @Operation(summary = "Cargar archivo")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileUploadResponse fileUploadResponse = fileService.uploadFile(file, subPath);
            return ResponseEntity.ok(fileUploadResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
    }

    @Operation(summary = "Obtener tapa de la recomendación")
    @GetMapping("/tapa/{filename}")
    public ResponseEntity<?> downloadCapa(@PathVariable String filename) {
        var fileBytes = fileService.getFile(subPath + "/" + filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileBytes);
    }

    @Operation(summary = "Obtener el contenido recomendación")
    @GetMapping("/contenido/{filename}")
    public ResponseEntity<?> downloadContenido(@PathVariable String filename) {
        var fileBytes = fileService.getFile(subPath + "/" + filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileBytes);
    }

}
