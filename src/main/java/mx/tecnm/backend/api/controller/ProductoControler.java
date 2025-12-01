package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.model.Producto;
import mx.tecnm.backend.api.service.ProductoService;

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

    @GetMapping("/{producto_id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable UUID producto_id) {
        Producto producto = service.obtenerPorId(producto_id);
        
        if (producto == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(producto); 
    }


    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto){
        Producto productoACrear = service.guardar(producto);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productoACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(productoACrear);
    }

    @PutMapping
    public ResponseEntity<Producto> actualizar(@RequestBody Producto producto){
        Producto productoAActualizar = service.actualizarPut(producto);
        if(productoAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(productoAActualizar);
    }

    @DeleteMapping("/{producto_id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID producto_id){
        int columnasAfectadas = service.eliminar(producto_id);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
