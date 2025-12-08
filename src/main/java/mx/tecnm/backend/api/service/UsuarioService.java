package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.dto.UsuarioDTO;
import mx.tecnm.backend.api.dto.UsuarioPutDTO;
import mx.tecnm.backend.api.exception.UsuarioNoEncontradoException;
import mx.tecnm.backend.api.dto.UsuarioPostDTO;
import mx.tecnm.backend.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository uRepo;

    public UsuarioService(UsuarioRepository uRepo) {
        this.uRepo = uRepo;
    }

    public List<UsuarioDTO> listar() {
        return uRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public UsuarioDTO obtenerPorId(UUID usuarioId) {
        Usuario usuario = uRepo.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(usuarioId));

        return this.toDTO(usuario);
    }

    public UsuarioDTO guardar(UsuarioPostDTO usuarioACrear){
        Usuario usuarioGuardado = uRepo.save(usuarioACrear);
        return this.toDTO(usuarioGuardado);
    }

    public UsuarioDTO actualizarPut(UsuarioPutDTO usuarioAActualizar, UUID usuarioId){
        Usuario usuarioActualizado = uRepo.update(usuarioAActualizar, usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException(usuarioId));
        
        return this.toDTO(usuarioActualizado);
    }

    public void eliminar(UUID usuarioId){
        int rows = uRepo.deactivateById(usuarioId);
        if(rows == 0){
            throw new UsuarioNoEncontradoException(usuarioId);
        }
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getTelefono(),
                u.getSexo(),
                u.getFechaNacimiento()
        );
    }
}