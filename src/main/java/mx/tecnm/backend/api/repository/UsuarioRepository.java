package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}