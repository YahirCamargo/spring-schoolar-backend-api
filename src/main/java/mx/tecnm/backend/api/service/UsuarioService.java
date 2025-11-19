package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepo;

    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<Usuario> listar() {
        return userRepo.findAll();
    }

    public Usuario obtener(UUID id){
        return userRepo.findById(id).orElse(null);
    }

    public Usuario guardar(Usuario user) {
        return userRepo.save(user);
    }

    public Usuario actualizarPut(UUID user_id,Usuario u){
        Usuario existente = obtener(user_id);
        if (existente != null) {
            existente.setNombre(u.getNombre());
            existente.setEmail(u.getEmail());
            existente.setTelefono(u.getTelefono());
            existente.setSexo(u.getSexo());
            existente.setFechaNacimiento(u.getFechaNacimiento());
            existente.setContrasena(u.getContrasena());
            existente.setRol(u.getRol());
            return guardar(existente);
        }
        return null;
    }

    public void eliminar(UUID user_id) {
        userRepo.deleteById(user_id);
    }
}