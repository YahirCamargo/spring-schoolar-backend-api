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
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios.")
@RestController
@RequestMapping("/api/usuarios")
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

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable UUID usuarioId) {
        UsuarioDTO usuario = service.obtenerPorId(usuarioId);
        
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

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable UUID usuarioId, @RequestBody UsuarioPutDTO user){
        UsuarioDTO usuarioAActualizar = service.actualizarPut(user,usuarioId);
        if(usuarioAActualizar == null){
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(usuarioAActualizar);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID usuarioId){
        int columnasAfectadas = service.eliminar(usuarioId);
        if(columnasAfectadas == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}