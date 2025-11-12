package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Domicilio;

public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
}