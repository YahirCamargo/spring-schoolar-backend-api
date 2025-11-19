package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.MetodoPago;
import java.util.UUID;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, UUID> {
}