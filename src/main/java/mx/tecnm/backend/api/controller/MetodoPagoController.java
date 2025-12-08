package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import mx.tecnm.backend.api.service.MetodoPagoService;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Metodos de Pago", description = "Endpoints para la gestión de los metodos de pago.")
@RestController
@RequestMapping("/api/metodos-pago")
@CrossOrigin(origins = "*")
public class MetodoPagoController {
    private final MetodoPagoService service;

    public MetodoPagoController(MetodoPagoService service) {
        this.service = service;
    }

    @GetMapping
    public List<MetodoPago> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtenerPorId(id)); 
    }


    @PostMapping
    public ResponseEntity<MetodoPago> crear(@RequestBody MetodoPagoPostDTO metodoPago){
        MetodoPago metodoPagoACrear = service.guardar(metodoPago);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(metodoPagoACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(metodoPagoACrear);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizar(@PathVariable UUID id,@RequestBody MetodoPagoPostDTO metodoPago){
        return ResponseEntity.ok(service.actualizarPut(metodoPago,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
