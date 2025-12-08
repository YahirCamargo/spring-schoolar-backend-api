package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import mx.tecnm.backend.api.dto.ProductoPutDTO;
import mx.tecnm.backend.api.dto.ProductoPostDTO;
import mx.tecnm.backend.api.model.Producto;
import mx.tecnm.backend.api.repository.ProductoRepository;
import mx.tecnm.backend.api.exception.ProductoNoEncontradoException;

@Service
public class ProductoService {
    private final ProductoRepository pRepo;

    public ProductoService(ProductoRepository pRepo) {
        this.pRepo = pRepo;
    }

    public List<Producto> listar() {
        return pRepo.findAll()
            .stream()
            .toList();
    }

    public Producto obtenerPorId(UUID productoId) {
        return pRepo.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
    }

    public Producto guardar(ProductoPostDTO productoACrear){
        Producto productoGuardado = pRepo.save(productoACrear);
        return productoGuardado;
    }

    public Producto actualizarPut(ProductoPutDTO productoAActualizar, UUID productoId){
        return pRepo.update(productoAActualizar,productoId)
                .orElseThrow(()-> new ProductoNoEncontradoException(productoId));
    }

    public void eliminar(UUID productoId){
        int rows = pRepo.deactivateById(productoId);
        if(rows == 0 ){
            throw new ProductoNoEncontradoException(productoId);
        }
    }
    
}
