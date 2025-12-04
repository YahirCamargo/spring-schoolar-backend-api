package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;

import mx.tecnm.backend.api.dto.DomicilioGetDTO;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.service.DomicilioService;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Direcciones", description = "Endpoints para la gestión de direcciones.")
@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
public class DomicilioController {
    private final DomicilioService service;

    public DomicilioController(DomicilioService service) {
        this.service = service;
    }

    @GetMapping
    public List<DomicilioGetDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{direccionId}")
    public ResponseEntity<DomicilioGetDTO> obtenerPorId(@PathVariable UUID direccionId) {
        DomicilioGetDTO domicilio = service.obtenerPorId(direccionId);
        
        if (domicilio == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(domicilio); 
    }

    @PostMapping
    public ResponseEntity<DomicilioGetDTO> crear(@RequestBody DomicilioPostDTO domicilio){
        DomicilioGetDTO domicilioACrear = service.guardar(domicilio);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(domicilioACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(domicilioACrear);
    }

    @DeleteMapping("/{direccionId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID direccionId){
        int columnasAfectadas = service.eliminar(direccionId);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
