package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioGetDTO;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.repository.DomicilioRepository;

@Service
public class DomicilioService {
    private final DomicilioRepository dRepo;

    public DomicilioService(DomicilioRepository dRepo) {
        this.dRepo = dRepo;
    }

    public List<DomicilioGetDTO> listar() {
        return dRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public DomicilioGetDTO obtenerPorId(UUID domicilio_id) {
        Domicilio domicilio = dRepo.findById(domicilio_id);
        if (domicilio == null){
            return null;
        }
        return this.toDTO(domicilio);
    }

    public DomicilioGetDTO guardar(DomicilioPostDTO domicilioACrear){
        if(domicilioACrear.getPreferido() == true){
            dRepo.unsetPreferredByUserId(domicilioACrear.getUsuarioId());
        }
        Domicilio domicilioGuardado = dRepo.save(domicilioACrear);
        return this.toDTO(domicilioGuardado);
    }


    public int eliminar(UUID domicilio_id){
        return dRepo.deactivateById(domicilio_id);
    }

    private DomicilioGetDTO toDTO(Domicilio d) {
        return new DomicilioGetDTO(
                d.getId(),
                d.getCalle(),
                d.getNumero(),
                d.getColonia(),
                d.getCp(),
                d.getEstado(),
                d.getCiudad(),
                d.isPreferido(),
                d.getUsuarioId()
        );
    }

    /*
    private final UUID id;
    private final String calle;
    private final String numero;
    private final String colonia;
    private final String cp;
    private final String estado;
    private final String ciudad;
    private final boolean preferido;
    private final UUID usuariosId; 
    */
}
