package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;

import mx.tecnm.backend.api.dto.ProductoPutDTO;
import mx.tecnm.backend.api.dto.ProductoGetDTO;
import mx.tecnm.backend.api.dto.ProductoPostDTO;
import mx.tecnm.backend.api.service.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Productos", description = "Endpoints para la gestión de productos.")
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoGetDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoGetDTO> obtenerPorId(@PathVariable UUID productoId) {
        ProductoGetDTO producto = service.obtenerPorId(productoId);
        
        if (producto == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(producto); 
    }


    @PostMapping
    public ResponseEntity<ProductoGetDTO> crear(@RequestBody ProductoPostDTO producto){
        ProductoGetDTO productoACrear = service.guardar(producto);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productoACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(productoACrear);
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<ProductoGetDTO> actualizar(@PathVariable UUID productoId,@RequestBody ProductoPutDTO producto){
        ProductoGetDTO productoAActualizar = service.actualizarPut(producto,productoId);
        if(productoAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(productoAActualizar);
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID productoId){
        int columnasAfectadas = service.eliminar(productoId);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
