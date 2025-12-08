package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.DetallePedido;
import mx.tecnm.backend.api.dto.DetallePedidoPostDTO;
import mx.tecnm.backend.api.dto.DetallePedidoPutDTO;
import mx.tecnm.backend.api.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {
    private final DetallePedidoRepository dPRepo;

    public DetallePedidoService(DetallePedidoRepository dPRepo) {
        this.dPRepo = dPRepo;
    }

    public List<DetallePedido> listar() {
        return dPRepo.findAll()
            .stream()
            .toList();
    }

    public DetallePedido obtenerPorId(UUID detallePedidoId) {
        DetallePedido detallePedido = dPRepo.findById(detallePedidoId);
        if (detallePedido == null){
            return null;
        }
        return detallePedido;
    }

    public DetallePedido guardar(DetallePedidoPostDTO detallePedidoACrear){
        DetallePedido detallePedidoGuardado = dPRepo.save(detallePedidoACrear);
        return detallePedidoGuardado;
    }

    public DetallePedido actualizarPut(DetallePedidoPutDTO detallePedidoAActualizar, UUID detallePedidoId){
        DetallePedido detallePedidoActualizado = dPRepo.update(detallePedidoAActualizar, detallePedidoId);
        if (detallePedidoActualizado == null){
            return null;
        }
        return detallePedidoActualizado;
    }

    public int eliminar(UUID detallePedidoId){
        return dPRepo.deactivateById(detallePedidoId);
    }
}
