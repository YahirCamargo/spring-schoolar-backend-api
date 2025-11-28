package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
import mx.tecnm.backend.api.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @GetMapping("/{categoria_id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable UUID categoria_id) {
        Categoria categoria = service.obtenerPorId(categoria_id);
        
        if (categoria == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(categoria); 
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody CategoriaPostDTO categoria){
        Categoria categoriaACrear = service.guardar(categoria);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(categoriaACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(categoriaACrear);
    }

    @PutMapping
    public ResponseEntity<Categoria> actualizar(@RequestBody Categoria categoria){
        Categoria categoriaAActualizar = service.actualizarPut(categoria);
        if(categoriaAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(categoriaAActualizar);
    }

    @DeleteMapping("/{categoria_id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID categoria_id){
        int columnasAfectadas = service.eliminar(categoria_id);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
