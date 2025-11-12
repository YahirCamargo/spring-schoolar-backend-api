package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.service.CategoriaService;

@RestController
@RequestMapping("/api/category")
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

    @GetMapping("/{category_id}")
    public Categoria obtener(@PathVariable Long category_id){
        return service.obtener(category_id);
    }

    @PostMapping
    public Categoria crear(@RequestBody Categoria category){
        return service.guardar(category);
    }

    @PutMapping("/{category_id}")
    public Categoria actualizar(@PathVariable Long category_id, @RequestBody Categoria c){
        return service.actualizarPut(category_id, c);
    }

    @DeleteMapping("/{category_id}")
    public void eliminar(@PathVariable Long category_id){
        service.eliminar(category_id);
    }

}