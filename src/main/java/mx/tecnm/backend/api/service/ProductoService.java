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

    public List<Producto> listar(){
        return pRepo.findAll();
    }

    public Producto obtener(UUID producto_id){
        return pRepo.findById(producto_id).orElse(null);
    }

    public Producto guardar(Producto p){
        return pRepo.save(p);
    }

    public Producto actualizarPut(UUID producto_id,Producto p){
        Producto existente = pRepo.findById(producto_id).orElse(null);
        if(existente != null){
            existente.setId(producto_id);
            existente.setNombre(p.getNombre());
            existente.setPrecio(p.getPrecio());
            existente.setSku(p.getSku());
            existente.setColor(p.getColor());
            existente.setMarca(p.getMarca());
            existente.setDescripcion(p.getDescripcion());
            existente.setPeso(p.getPeso());
            existente.setAlto(p.getAlto());
            existente.setAncho(p.getAncho());
            existente.setProfundidad(p.getProfundidad());
            return guardar(existente);
        }
        return null;
    }

    public void eliminar(UUID producto_id){
        pRepo.deleteById(producto_id);
    }
}
