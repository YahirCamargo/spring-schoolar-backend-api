package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.dto.CategoriaGetDTO;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
import mx.tecnm.backend.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Categorías", description = "Endpoints para la gestión de categorias.")
@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaGetDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaGetDTO> obtenerPorId(@PathVariable UUID categoriaId) {
        CategoriaGetDTO categoria = service.obtenerPorId(categoriaId);
        
        if (categoria == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(categoria); 
    }

    @PostMapping
    public ResponseEntity<CategoriaGetDTO> crear(@RequestBody CategoriaPostDTO categoria){
        CategoriaGetDTO categoriaACrear = service.guardar(categoria);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(categoriaACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(categoriaACrear);
    }

    @PutMapping("/{categoriaId}")
    public ResponseEntity<CategoriaGetDTO> actualizar(@PathVariable UUID categoriaId, @RequestBody CategoriaPostDTO categoria){
        CategoriaGetDTO categoriaAActualizar = service.actualizarPut(categoria,categoriaId);
        if(categoriaAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(categoriaAActualizar);
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID categoriaId){
        int columnasAfectadas = service.eliminar(categoriaId);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
