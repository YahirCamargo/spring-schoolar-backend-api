package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Usuario;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}