package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Producto;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID>{

}
