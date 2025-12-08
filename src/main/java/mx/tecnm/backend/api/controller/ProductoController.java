package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.model.Producto;
import mx.tecnm.backend.api.dto.ProductoPutDTO;
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
    public List<Producto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obtenerPorId(id)); 
    }


    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody ProductoPostDTO producto){
        Producto productoACrear = service.guardar(producto);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productoACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(productoACrear);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable UUID id,@RequestBody ProductoPutDTO producto){
        return ResponseEntity.ok(service.actualizarPut(producto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
