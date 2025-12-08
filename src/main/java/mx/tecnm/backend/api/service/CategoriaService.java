package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
import mx.tecnm.backend.api.exception.CategoriaNoEncontradaException;
import mx.tecnm.backend.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository cRepo;

    public CategoriaService(CategoriaRepository cRepo) {
        this.cRepo = cRepo;
    }

    public List<Categoria> listar() {
        return cRepo.findAll()
            .stream()
            .toList();
    }

    public Categoria obtenerPorId(UUID categoriaId) {
        return cRepo.findById(categoriaId)
            .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
    }

    public Categoria guardar(CategoriaPostDTO categoriaACrear){
        Categoria categoriaGuardado = cRepo.save(categoriaACrear);
        return categoriaGuardado;
    }

    public Categoria actualizarPut(CategoriaPostDTO categoriaAActualizar, UUID categoriaId){
        return cRepo.update(categoriaAActualizar, categoriaId)
        .orElseThrow(() -> new CategoriaNoEncontradaException(categoriaId));
    }

    public void eliminar(UUID categoriaId){
        int rows = cRepo.deactivateById(categoriaId);
        if(rows == 0){
            throw new CategoriaNoEncontradaException(categoriaId);
        }
    }

}
