package mx.tecnm.backend.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import mx.tecnm.backend.api.dto.EnvioPostDTO;
import mx.tecnm.backend.api.dto.EnvioPutDTO;
import mx.tecnm.backend.api.model.Envio;
import mx.tecnm.backend.api.repository.EnvioRepository;

@Service
public class EnvioService {
    private final EnvioRepository eRepo;

    public EnvioService(EnvioRepository eRepo) {
        this.eRepo = eRepo;
    }

    public List<Envio> listar() {
        return eRepo.findAll()
            .stream()
            .toList();
    }

    public Envio obtenerPorId(UUID envioId) {
        Envio envio = eRepo.findById(envioId);
        if (envio == null){
            return null;
        }
        return envio;
    }

    public Envio guardar(EnvioPostDTO envioACrear){
        String random = "TRK-" +String.valueOf((int)(Math.random()*900000) + 100000);
        Envio envioGuardado = eRepo.save(envioACrear,random);
        return envioGuardado;
    }

    public Envio actualizarPut(EnvioPutDTO envioAActualizar, UUID envioId){
        Envio envioActualizado = eRepo.update(envioAActualizar, envioId);
        if (envioAActualizar == null){
            return null;
        }
        return envioActualizado;
    }

    public int eliminar(UUID envioId){
        return eRepo.deactivateById(envioId);
    }
}
