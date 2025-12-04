package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import mx.tecnm.backend.api.dto.MetodoPagoGetDTO;
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
    public List<MetodoPagoGetDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{metodoPagoId}")
    public ResponseEntity<MetodoPagoGetDTO> obtenerPorId(@PathVariable UUID metodoPagoId) {
        MetodoPagoGetDTO metodoPago = service.obtenerPorId(metodoPagoId);
        
        if (metodoPago == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(metodoPago); 
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

    @PutMapping("/{metodoPagoId}")
    public ResponseEntity<MetodoPago> actualizar(@PathVariable UUID metodoPagoId,@RequestBody MetodoPagoPostDTO metodoPago){
        MetodoPago metodoPagoAActualizar = service.actualizarPut(metodoPago,metodoPagoId);
        if(metodoPagoAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(metodoPagoAActualizar);
    }

    @DeleteMapping("/{metodoPagoId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID metodoPagoId){
        int columnasAfectadas = service.eliminar(metodoPagoId);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
