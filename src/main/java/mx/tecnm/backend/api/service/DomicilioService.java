package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.repository.DomicilioRepository;

@Service
public class DomicilioService {
    private final DomicilioRepository dRepo;

    public DomicilioService(DomicilioRepository dRepo) {
        this.dRepo = dRepo;
    }

    public List<Domicilio> listar() {
        return dRepo.findAll()
            .stream()
            .toList();
    }

    public Domicilio obtenerPorId(UUID domicilio_id) {
        Domicilio domicilio = dRepo.findById(domicilio_id);
        if (domicilio == null){
            return null;
        }
        return domicilio;
    }

    public Domicilio guardar(DomicilioPostDTO domicilioACrear){
        if(domicilioACrear.getPreferido() == true){
            dRepo.unsetPreferredByUserId(domicilioACrear.getUsuarioId());
        }
        Domicilio domicilioGuardado = dRepo.save(domicilioACrear);
        return domicilioGuardado;
    }

    /* 
    public Domicilio actualizarPut(DomicilioPutDTO domicilio){
        if(dRepo.findById(domicilio.getId()) == null){
            return null;
        }
        if(domicilio.getPreferido() == true){
            dRepo.unsetPreferredByUserId(domicilio.getUsuarioId());
        }
        Domicilio domicilioAActualizar = dRepo.update(domicilio);
        return domicilioAActualizar;
    }
    */

    public int eliminar(UUID domicilio_id){
        return dRepo.deleteById(domicilio_id);
    }
}
