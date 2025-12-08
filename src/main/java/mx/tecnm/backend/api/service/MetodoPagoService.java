package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import mx.tecnm.backend.api.exception.MetodoPagoNoEncontradoException;
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

    public MetodoPago obtenerPorId(UUID metodoPagoId) {
        return mpRepo.findById(metodoPagoId)
                .orElseThrow(() -> new MetodoPagoNoEncontradoException(metodoPagoId));
    }

    public MetodoPago guardar(MetodoPagoPostDTO metodoPagoACrear){
        MetodoPago metodoPagoGuardado = mpRepo.save(metodoPagoACrear);
        return metodoPagoGuardado;
    }

    public MetodoPago actualizarPut(MetodoPagoPostDTO metodoPagoAActualizar, UUID metodoPagoId){
        return mpRepo.update(metodoPagoAActualizar,metodoPagoId)
                .orElseThrow(() -> new MetodoPagoNoEncontradoException(metodoPagoId));
    }

    public void eliminar(UUID metodoPagoId){
        int rows = mpRepo.deleteById(metodoPagoId);
        if(rows == 0){
            throw new MetodoPagoNoEncontradoException(metodoPagoId);
        }
    }

}
