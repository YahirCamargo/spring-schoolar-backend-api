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

@RestController
@RequestMapping("/api/metodo-pago")
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

    @GetMapping("/{metodo_pago_id}")
    public ResponseEntity<MetodoPago> obtenerPorId(@PathVariable UUID metodo_pago_id) {
        MetodoPago metodoPago = service.obtenerPorId(metodo_pago_id);
        
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

    @PutMapping
    public ResponseEntity<MetodoPago> actualizar(@RequestBody MetodoPago metodoPago){
        MetodoPago metodoPagoAActualizar = service.actualizarPut(metodoPago);
        if(metodoPagoAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(metodoPagoAActualizar);
    }

    @DeleteMapping("/{metodo_pago_id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID metodo_pago_id){
        int columnasAfectadas = service.eliminar(metodo_pago_id);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
