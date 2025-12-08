package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.dto.DomicilioPutDTO;
import mx.tecnm.backend.api.exception.DomicilioNoEncontradoException;
import mx.tecnm.backend.api.exception.UsuarioNoEncontradoException;
import mx.tecnm.backend.api.repository.DomicilioRepository;
import mx.tecnm.backend.api.repository.UsuarioRepository;

@Service
public class DomicilioService {
    private final DomicilioRepository dRepo;
    private final UsuarioRepository uRepo;

    public DomicilioService(DomicilioRepository dRepo,UsuarioRepository uRepo) {
        this.dRepo = dRepo;
        this.uRepo = uRepo;
    }

    public List<Domicilio> listar() {
        return dRepo.findAll()
            .stream()
            .toList();
    }

    public Domicilio obtenerPorId(UUID domicilioId) {
        return dRepo.findById(domicilioId)
        .orElseThrow(() -> new DomicilioNoEncontradoException(domicilioId));
    }

    @Transactional
    public Domicilio guardar(DomicilioPostDTO domicilioACrear){
        uRepo.findById(domicilioACrear.getUsuarioId())
                .orElseThrow(() -> new UsuarioNoEncontradoException(domicilioACrear.getUsuarioId()));

        if(domicilioACrear.isPreferido()){
            dRepo.unsetPreferredByUserId(domicilioACrear.getUsuarioId());
        }

        Domicilio domicilioGuardado = dRepo.save(domicilioACrear);
        return domicilioGuardado;
    }

    @Transactional
    public Domicilio actualizar(DomicilioPutDTO domicilioAActualizar, UUID domicilioId){
        Domicilio domicilio = dRepo.findById(domicilioId)
            .orElseThrow(() -> new DomicilioNoEncontradoException(domicilioId));;

        if(domicilioAActualizar.isPreferido()){
            dRepo.unsetPreferredByUserId(domicilio.getUsuarioId());
        }

        Domicilio domicilioActualizado = dRepo.update(domicilioAActualizar, domicilioId)
            .orElseThrow(() -> new DomicilioNoEncontradoException(domicilioId));
        return domicilioActualizado;
    }


    public void eliminar(UUID domicilioId){
        int rows = dRepo.deactivateById(domicilioId);
        if(rows == 0){
            throw new DomicilioNoEncontradoException(domicilioId);
        }
    }
}
