package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.net.URI;
import java.util.UUID;
import mx.tecnm.backend.api.dto.UsuarioDTO;
import mx.tecnm.backend.api.dto.UsuarioPostDTO;
import mx.tecnm.backend.api.dto.UsuarioPutDTO;
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
    public List<UsuarioDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable UUID user_id) {
        UsuarioDTO usuario = service.obtenerPorId(user_id);
        
        if (usuario == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(usuario); 
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioPostDTO user){
        UsuarioDTO usuarioACrear = service.guardar(user);
        URI ubicacion = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(usuarioACrear.getId())
            .toUri();
        return ResponseEntity.created(ubicacion).body(usuarioACrear);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> actualizar(@RequestBody UsuarioPutDTO user){
        UsuarioDTO usuarioAActualizar = service.actualizarPut(user);
        if(usuarioAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(usuarioAActualizar);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID user_id){
        int columnasAfectadas = service.eliminar(user_id);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

/* 
    @GetMapping("/{user_id}")
    public Usuario obtener(@PathVariable UUID user_id){
        return service.obtener(user_id);
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario user){
        return service.guardar(user);
    }

    @PutMapping("/{user_id}")
    public Usuario actualizar(@PathVariable UUID user_id, @RequestBody Usuario u) {
        return service.actualizarPut(user_id, u);
    }

    @DeleteMapping("/{user_id}")
    public void eliminar(@PathVariable UUID user_id){
        service.eliminar(user_id);
    }
*/
}