package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import mx.tecnm.backend.api.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    private final MetodoPagoRepository mpRepo;

    public MetodoPagoService(MetodoPagoRepository mpRepo) {
        this.mpRepo = mpRepo;
    }

    public List<MetodoPago> listar() {
        return mpRepo.findAll()
            .stream()
            .toList();
    }

    public MetodoPago obtenerPorId(UUID metodo_pago_id) {
        MetodoPago metodoPago = mpRepo.findById(metodo_pago_id);
        if (metodoPago == null){
            return null;
        }
        return metodoPago;
    }

    public MetodoPago guardar(MetodoPagoPostDTO metodoPagoACrear){
        MetodoPago metodoPagoGuardado = mpRepo.save(metodoPagoACrear);
        return metodoPagoGuardado;
    }

    public MetodoPago actualizarPut(MetodoPago metodoPago){
        MetodoPago metodoPagoAActualizar = mpRepo.update(metodoPago);
        if (metodoPagoAActualizar == null){
            return null;
        }
        return metodoPagoAActualizar;
    }

    public int eliminar(UUID metodo_pago_id){
        return mpRepo.deleteById(metodo_pago_id);
    }
}
