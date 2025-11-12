package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.service.UsuarioService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{user_id}")
    public Usuario obtener(@PathVariable Long user_id){
        return service.obtener(user_id);
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario user){
        return service.guardar(user);
    }

    @PutMapping("/{user_id}")
    public Usuario actualizar(@PathVariable Long user_id, @RequestBody Usuario u) {
        return service.actualizarPut(user_id, u);
    }

    @DeleteMapping("/{user_id}")
    public void eliminar(@PathVariable Long user_id){
        service.eliminar(user_id);
    }
}