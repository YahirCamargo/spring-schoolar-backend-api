package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;

import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.dto.DomicilioPutDTO;
import mx.tecnm.backend.api.service.DomicilioService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Domicilios", description = "Endpoints para la gestión de domicilios.")
@RestController
@RequestMapping("/api/domicilios")
@CrossOrigin(origins = "*")
public class DomicilioController {
    private final DomicilioService service;

    public DomicilioController(DomicilioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Domicilio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtenerPorId(id)); 
    }

    @PostMapping
    public ResponseEntity<Domicilio> crear(@RequestBody DomicilioPostDTO domicilio){
        Domicilio domicilioACrear = service.guardar(domicilio);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(domicilioACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(domicilioACrear);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Domicilio> actualizar(@PathVariable UUID id, @RequestBody DomicilioPutDTO domicilio) {
        return ResponseEntity.ok(service.actualizar(domicilio, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
