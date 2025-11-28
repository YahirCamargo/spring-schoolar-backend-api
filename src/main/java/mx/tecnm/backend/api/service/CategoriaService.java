package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
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

    public Categoria obtenerPorId(UUID categoria_id) {
        Categoria categoria = cRepo.findById(categoria_id);
        if (categoria == null){
            return null;
        }
        return categoria;
    }

    public Categoria guardar(CategoriaPostDTO categoriaACrear){
        Categoria categoriaGuardado = cRepo.save(categoriaACrear);
        return categoriaGuardado;
    }

    public Categoria actualizarPut(Categoria categoria){
        Categoria categoriaAActualizar = cRepo.update(categoria);
        if (categoriaAActualizar == null){
            return null;
        }
        return categoriaAActualizar;
    }

    public int eliminar(UUID categoria_id){
        return cRepo.deactivateById(categoria_id);
    }
}
