package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.repository.DomicilioRepository;

@Service
public class DomicilioService {

    private final DomicilioRepository dRepo;

    public DomicilioService(DomicilioRepository dRepo) {
        this.dRepo = dRepo;
    }

    public List<Domicilio> listar() {
        return dRepo.findAll();
    }

    public Domicilio obtener(Long address_id){
        return dRepo.findById(address_id).orElse(null);
    }

    public Domicilio guardar(Domicilio domicilio){
        return dRepo.save(domicilio);
    }

    public Domicilio actualizarPut(Long address_id, Domicilio d){
        Domicilio existente = dRepo.findById(address_id).orElse(null);
        if(existente != null){
            existente.setId(d.getId());
            existente.setCalle(d.getCalle());
            existente.setNumero(d.getNumero());
            existente.setColonia(d.getColonia());
            existente.setCp(d.getCp());
            existente.setEstado(d.getEstado());
            existente.setCiudad(d.getCiudad());
            existente.setPreferido(d.isPreferido());
            return guardar(existente);
        }
        return null;
    }
}