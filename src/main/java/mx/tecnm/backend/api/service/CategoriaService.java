package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository cRepo;

    public CategoriaService(CategoriaRepository cRepo) {
        this.cRepo = cRepo;
    }

    public List<Categoria> listar() {
        return cRepo.findAll();
    }

    public Categoria obtener(Long id){
        return cRepo.findById(id).orElse(null);
    }

    public Categoria guardar(Categoria cat){
        return cRepo.save(cat);
    }

    public Categoria actualizarPut(Long category_id,Categoria c){
        Categoria existente = obtener(category_id);
        if (existente != null) {
            existente.setId(c.getId());
            existente.setNombre(c.getNombre());
            return guardar(existente);
        }
        return null;
    }

    public void eliminar(Long category_id){
        cRepo.deleteById(category_id);
    }
}