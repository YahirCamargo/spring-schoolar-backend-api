package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}