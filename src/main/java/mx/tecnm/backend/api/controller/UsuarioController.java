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

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable UUID id) {        
        return ResponseEntity.ok(service.obtenerPorId(id)); 
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

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable UUID id, @RequestBody UsuarioPutDTO user){
        return ResponseEntity.ok(service.actualizarPut(user,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}