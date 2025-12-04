package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import mx.tecnm.backend.api.dto.ProductoPutDTO;
import mx.tecnm.backend.api.dto.ProductoGetDTO;
import mx.tecnm.backend.api.dto.ProductoPostDTO;
import mx.tecnm.backend.api.model.Producto;
import mx.tecnm.backend.api.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository pRepo;

    public ProductoService(ProductoRepository pRepo) {
        this.pRepo = pRepo;
    }

    public List<ProductoGetDTO> listar() {
        return pRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public ProductoGetDTO obtenerPorId(UUID producto_id) {
        Producto producto = pRepo.findById(producto_id);
        if (producto == null){
            return null;
        }
        return this.toDTO(producto);
    }

    public ProductoGetDTO guardar(ProductoPostDTO productoACrear){
        Producto productoGuardado = pRepo.save(productoACrear);
        return this.toDTO(productoGuardado);
    }

    public ProductoGetDTO actualizarPut(ProductoPutDTO producto, UUID productoId){
        Producto productoAActualizar = pRepo.update(producto,productoId);
        if (productoAActualizar == null){
            return null;
        }
        return this.toDTO(productoAActualizar);
    }

    public int eliminar(UUID producto_id){
        return pRepo.deactivateById(producto_id);
    }


    private ProductoGetDTO toDTO(Producto p){
        return new ProductoGetDTO(
            p.getId(), 
            p.getNombre(), 
            p.getPrecio(), 
            p.getSku(), 
            p.getColor(), 
            p.getMarca(), 
            p.getDescripcion(), 
            p.getPeso(), 
            p.getAlto(), 
            p.getAncho(), 
            p.getProfundidad(), 
            p.getCategoriasId()
        );
    }
    
}
