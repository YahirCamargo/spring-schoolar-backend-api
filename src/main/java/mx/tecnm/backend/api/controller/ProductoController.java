package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import mx.tecnm.backend.api.model.Producto;
import mx.tecnm.backend.api.service.ProductoService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoService service;
    
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> listar(){
        return service.listar();
    }

    @GetMapping("/{producto_id}")
    public Producto obtener(@PathVariable UUID producto_id){
        return service.obtener(producto_id);
    }

    @PostMapping
    public Producto guardar(@RequestBody Producto producto){
        return service.guardar(producto);
    }

    @PutMapping("/{producto_id}")
    public Producto actualizar(@PathVariable UUID producto_id, @RequestBody Producto producto){
        return service.actualizarPut(producto_id, producto);
    }

    @DeleteMapping("/{producto_id}")
    public void eliminar(@PathVariable UUID producto_id){
        service.eliminar(producto_id);
    }
}
