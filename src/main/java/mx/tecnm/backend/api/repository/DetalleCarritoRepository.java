package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.DetalleCarrito;
import java.util.UUID;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, UUID>{

}
