package mx.tecnm.backend.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.tecnm.backend.api.model.Pedido;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID>{

}
