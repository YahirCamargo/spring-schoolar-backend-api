package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.dto.UsuarioDTO;
import mx.tecnm.backend.api.dto.UsuarioPutDTO;
import mx.tecnm.backend.api.dto.UsuarioPostDTO;
import mx.tecnm.backend.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepo;

    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UsuarioDTO> listar() {
        return userRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public UsuarioDTO obtenerPorId(UUID usuario_id) {
        Usuario usuario = userRepo.findById(usuario_id);
        if (usuario == null){
            return null;
        }
        return this.toDTO(usuario);
    }

    public UsuarioDTO guardar(UsuarioPostDTO usuarioACrear){
        Usuario usuarioGuardado = userRepo.save(usuarioACrear);
        return this.toDTO(usuarioGuardado);
    }

    public UsuarioDTO actualizarPut(UsuarioPutDTO usuario){
        Usuario usuarioAActualizar = userRepo.update(usuario);
        if (usuarioAActualizar == null){
            return null;
        }
        return this.toDTO(usuarioAActualizar);
    }

    public int eliminar(UUID usuario_id){
        return userRepo.deactivateById(usuario_id);
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