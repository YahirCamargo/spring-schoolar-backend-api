package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Producto;
import mx.tecnm.backend.api.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository pRepo;

    public ProductoService(ProductoRepository pRepo) {
        this.pRepo = pRepo;
    }

    public List<Producto> listar() {
        return pRepo.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public Producto obtenerPorId(UUID producto_id) {
        Producto producto = pRepo.findById(producto_id);
        if (producto == null){
            return null;
        }
        return this.toDTO(producto);
    }

    public Producto guardar(Producto productoACrear){
        Producto productoGuardado = pRepo.save(productoACrear);
        return productoGuardado;
    }

    public Producto actualizarPut(Producto producto){
        Producto productoAActualizar = pRepo.update(producto);
        if (productoAActualizar == null){
            return null;
        }
        return productoAActualizar;
    }

    public int eliminar(UUID producto_id){
        return pRepo.deleteById(producto_id);
    }

    private Producto toDTO(Producto m) {
        return new ProductoGetDTO(
                m.getId(),
                m.getNombre(),
                m.getComision()
        );
    }
}
