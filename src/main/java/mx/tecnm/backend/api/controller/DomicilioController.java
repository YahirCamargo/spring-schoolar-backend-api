package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.service.DomicilioService;

@RestController
@RequestMapping("/api/addresses")
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

    @GetMapping("/{domicilio_id}")
    public ResponseEntity<Domicilio> obtenerPorId(@PathVariable UUID domicilio_id) {
        Domicilio domicilio = service.obtenerPorId(domicilio_id);
        
        if (domicilio == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(domicilio); 
    }

    @PostMapping
    public ResponseEntity<Domicilio> crear(@RequestBody DomicilioPostDTO domicilio){
        Domicilio domicilioACrear = service.guardar(domicilio);
        URI ubicacion = URI.create("/addresses/" + domicilioACrear.getId());
        return ResponseEntity.created(ubicacion).body(domicilioACrear);
    }

    /* 
    @PutMapping
    public ResponseEntity<Domicilio> actualizar(@RequestBody Domicilio domicilio){
        Domicilio domicilioAActualizar = service.actualizarPut(domicilio);
        if(domicilioAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(domicilioAActualizar);
    }
    */

    @DeleteMapping("/{domicilio_id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID domicilio_id){
        int columnasAfectadas = service.eliminar(domicilio_id);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
