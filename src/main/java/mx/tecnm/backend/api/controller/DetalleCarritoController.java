package mx.tecnm.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.DetalleCarrito;
import mx.tecnm.backend.api.dto.DetalleCarritoPostDTO;
import mx.tecnm.backend.api.dto.DetalleCarritoPutDTO;
import mx.tecnm.backend.api.service.DetalleCarritoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Carritos", description = "Endpoints para la gestión de carritos.")
@RestController
@RequestMapping("/api/detalles-carritos")
public class DetalleCarritoController {

    private final DetalleCarritoService detalleCarritoService;

    public DetalleCarritoController(DetalleCarritoService detalleCarritoService){
        this.detalleCarritoService = detalleCarritoService;
    }

    @GetMapping
    public List<DetalleCarrito> listar(){
        return detalleCarritoService.listar();
    }

    @GetMapping("/{id}")
    public DetalleCarrito obtenerPorId(@PathVariable UUID id){
        return detalleCarritoService.obtenerPorId(id);
    }

    @PostMapping
    public DetalleCarrito guardar(@RequestBody DetalleCarritoPostDTO dto){
        return detalleCarritoService.guardar(dto);
    }

    @PutMapping("/{id}")
    public DetalleCarrito actualizarPut(@RequestBody DetalleCarritoPutDTO dto, @PathVariable UUID id){
        return detalleCarritoService.actualizarPut(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        detalleCarritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
