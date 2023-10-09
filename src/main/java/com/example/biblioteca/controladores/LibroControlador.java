package com.example.biblioteca.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.modelos.FileUploadResponse;
import com.example.biblioteca.modelos.Libro;
import com.example.biblioteca.servicios.FileService;
import com.example.biblioteca.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Value("${host.url}")
    private String host;

    @Value("${server.port}")
    private String port;

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private FileService fileService;
    private String subPath = "libro";

    @Operation(summary = "Listar libros")
    @GetMapping(value = "todos")
    public ArrayList<Libro> listar() {
        return this.libroServicio.listarLibros();
    }

    @Operation(summary = "Obtener libro")
    @GetMapping(value = "/{id}")
    public Optional<Libro> obtenerPorId(@PathVariable Long id) {
        return libroServicio.obtenerPorId(id);
    }

    @Operation(summary = "Guardar libro")
    @PostMapping()
    public ResponseEntity<Libro> guardar(@RequestBody Libro libro) {
        try {
            return ResponseEntity.status(201).body(this.libroServicio.guardar(libro));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @Operation(summary = "Guardar libro mediante formulario form data")
    @PostMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Libro> guardarFormData(@RequestParam(name = "contenido") MultipartFile contenido,
            @RequestParam("tapa") MultipartFile tapa,
            @RequestParam("autor") String autor,
            @RequestParam("titulo") String titulo,
            @RequestParam("numeroPaginas") Integer numeroPaginas,
            @RequestParam("sinopsis") String sinopsis) {

        Libro l = new Libro();
        try {
            FileUploadResponse fl = fileService.uploadFile(contenido, subPath);
            String url = String.format("%s:%s/libro/contenido/%s", host, port, fl.getFilename());
            l.setContenido(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }

        try {
            FileUploadResponse fl = fileService.uploadFile(tapa, subPath);
            String url = String.format("%s:%s/libro/tapa/%s", host, port, fl.getFilename());
            l.setTapa(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }

        l.setAutor(autor);
        l.setNumeroPaginas(numeroPaginas);
        l.setSinopsis(sinopsis);
        l.setTitulo(titulo);
        return ResponseEntity.ok(this.libroServicio.guardar(l));
    }

    @Operation(summary = "Actualizar libro")
    @PutMapping
    public ResponseEntity<Libro> actualizar(@RequestBody Libro libro) {
        try {
            return ResponseEntity.ok(this.libroServicio.actualizar(libro));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Actualizar libro mediante formulario form data")
    @PutMapping(value = "form-data", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<Libro> actualizarFormData(@RequestParam("contenido") MultipartFile contenido,
            @RequestParam("tapa") MultipartFile tapa,
            @RequestParam("id") Long id,
            @RequestParam("autor") String autor,
            @RequestParam("titulo") String titulo,
            @RequestParam("numeroPaginas") Integer numeroPaginas,
            @RequestParam("sinopsis") String sinopsis) {

        Libro l = new Libro();
        try {
            FileUploadResponse fl = fileService.uploadFile(contenido, subPath);
            String url = String.format("%s:%s/libro/contenido/%s", host, port, fl.getFilename());
            l.setContenido(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }

        try {
            FileUploadResponse fl = fileService.uploadFile(tapa, subPath);
            String url = String.format("%s:%s/libro/tapa/%s", host, port, fl.getFilename());
            l.setTapa(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
        l.setId(id);
        l.setAutor(autor);
        l.setNumeroPaginas(numeroPaginas);
        l.setSinopsis(sinopsis);
        l.setTitulo(titulo);
        return ResponseEntity.ok(this.libroServicio.actualizar(l));
    }

    @Operation(summary = "Eliminar libro")
    @DeleteMapping(path = "{id}")
    public String eliminar(@PathVariable Long id) {
        boolean ok = this.libroServicio.eliminar(id);
        if (ok) {
            return "Libro eliminado";
        }
        return "Libro no eliminado";
    }

    @Operation(summary = "Cargar archivo")
    @PostMapping(value = "/upload", consumes = {
            "multipart/form-data"
    })
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileUploadResponse fileUploadResponse = fileService.uploadFile(file, subPath);
            return ResponseEntity.ok(fileUploadResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .build();
        }
    }

    @Operation(summary = "Obtener tapa de libro")
    @GetMapping("/tapa/{filename}")
    public ResponseEntity<?> downloadCapa(@PathVariable String filename) {
        var fileBytes = fileService.getFile(subPath + "/" + filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileBytes);
    }

    @Operation(summary = "Contenido del libro")
    @GetMapping("/contenido/{filename}")
    public ResponseEntity<?> downloadContenido(@PathVariable String filename) {
        var fileBytes = fileService.getFile(subPath + "/" + filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileBytes);
    }

}
