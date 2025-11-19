package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Categoria;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}