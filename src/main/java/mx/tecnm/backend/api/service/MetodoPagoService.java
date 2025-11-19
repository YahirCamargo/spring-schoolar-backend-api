package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository mePaRepo;

    public MetodoPagoService(MetodoPagoRepository mePaRepo) {
        this.mePaRepo = mePaRepo;
    }

    public List<MetodoPago> listar(){
        return mePaRepo.findAll();
    }

    public MetodoPago obtener(UUID metodo_pago_id){
        return mePaRepo.findById(metodo_pago_id).orElse(null);
    }

    public MetodoPago guardar(MetodoPago mP){
        return mePaRepo.save(mP);
    }

    public MetodoPago actualizarPut(UUID metodo_pago_id,MetodoPago mP){
        MetodoPago existente = mePaRepo.findById(metodo_pago_id).orElse(null);
        if(existente != null){
            existente.setId(mP.getId());
            existente.setNombre(mP.getNombre());
            existente.setComision(mP.getComision());
            return guardar(existente);
        }
        return null;
    }

    public void eliminar(UUID metodo_pago_id){
        mePaRepo.deleteById(metodo_pago_id);
    }
}