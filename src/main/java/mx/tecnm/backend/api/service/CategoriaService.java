package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.dto.CategoriaGetDTO;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
import mx.tecnm.backend.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository cRepo;

    public CategoriaService(CategoriaRepository cRepo) {
        this.cRepo = cRepo;
    }

    public List<CategoriaGetDTO> listar() {
        return cRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public CategoriaGetDTO obtenerPorId(UUID categoria_id) {
        Categoria categoria = cRepo.findById(categoria_id);
        if (categoria == null){
            return null;
        }
        return this.toDTO(categoria);
    }

    public CategoriaGetDTO guardar(CategoriaPostDTO categoriaACrear){
        Categoria categoriaGuardado = cRepo.save(categoriaACrear);
        return this.toDTO(categoriaGuardado);
    }

    public CategoriaGetDTO actualizarPut(CategoriaPostDTO categoria, UUID categoriaId){
        Categoria categoriaAActualizar = cRepo.update(categoria, categoriaId);
        if (categoriaAActualizar == null){
            return null;
        }
        return this.toDTO(categoriaAActualizar);
    }

    public int eliminar(UUID categoria_id){
        return cRepo.deactivateById(categoria_id);
    }

    private CategoriaGetDTO toDTO(Categoria c) {
        return new CategoriaGetDTO(
                c.getId(),
                c.getNombre()
        );
    }
}
