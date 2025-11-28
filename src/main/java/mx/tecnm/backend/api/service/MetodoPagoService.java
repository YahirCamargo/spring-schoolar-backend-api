package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import mx.tecnm.backend.api.dto.MetodoPagoGetDTO;
import mx.tecnm.backend.api.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    private final MetodoPagoRepository mpRepo;

    public MetodoPagoService(MetodoPagoRepository mpRepo) {
        this.mpRepo = mpRepo;
    }

    public List<MetodoPagoGetDTO> listar() {
        return mpRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public MetodoPagoGetDTO obtenerPorId(UUID metodo_pago_id) {
        MetodoPago metodoPago = mpRepo.findById(metodo_pago_id);
        if (metodoPago == null){
            return null;
        }
        return this.toDTO(metodoPago);
    }

    public MetodoPago guardar(MetodoPagoPostDTO metodoPagoACrear){
        MetodoPago metodoPagoGuardado = mpRepo.save(metodoPagoACrear);
        return metodoPagoGuardado;
    }

    public MetodoPago actualizarPut(MetodoPagoGetDTO metodoPago){
        MetodoPago metodoPagoAActualizar = mpRepo.update(metodoPago);
        if (metodoPagoAActualizar == null){
            return null;
        }
        return metodoPagoAActualizar;
    }

    public int eliminar(UUID metodo_pago_id){
        return mpRepo.deleteById(metodo_pago_id);
    }

    private MetodoPagoGetDTO toDTO(MetodoPago m) {
        return new MetodoPagoGetDTO(
                m.getId(),
                m.getNombre(),
                m.getComision()
        );
    }
}
