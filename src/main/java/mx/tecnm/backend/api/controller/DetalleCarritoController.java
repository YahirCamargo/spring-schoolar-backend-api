package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.DetalleCarrito;
import mx.tecnm.backend.api.service.DetalleCarritoService;

@RestController
@RequestMapping("/api/detalle-carrito")
@CrossOrigin(origins = "*")
public class DetalleCarritoController {
    private final DetalleCarritoService service;
    
    public DetalleCarritoController(DetalleCarritoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetalleCarrito> listar(){
        return service.listar();
    }

    @GetMapping("/{detalle_carrito_id}")
    public DetalleCarrito obtener(@PathVariable UUID detalle_carrito_id){
        return service.obtener(detalle_carrito_id);
    }

    @PostMapping
    public DetalleCarrito guardar(@RequestBody DetalleCarrito detalleCarrito){
        return service.guardar(detalleCarrito);
    }

    @PutMapping("/{detalle_carrito_id}")
    public DetalleCarrito actualizar(@PathVariable UUID detalle_carrito_id, @RequestBody DetalleCarrito detalleCarrito){
        return service.actualizarPut(detalle_carrito_id, detalleCarrito);
    }

    @DeleteMapping("/{detalle_carrito_id}")
    public void eliminar(@PathVariable UUID detalle_carrito_id){
        service.eliminar(detalle_carrito_id);
    }
}
