package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.DetalleCarrito;
import mx.tecnm.backend.api.repository.DetalleCarritoRepository;

@Service
public class DetalleCarritoService {
    private final DetalleCarritoRepository deCaRepo;

    public DetalleCarritoService(DetalleCarritoRepository deCaRepo) {
        this.deCaRepo = deCaRepo;
    }

    public List<DetalleCarrito> listar() {
        return deCaRepo.findAll();
    }

    public DetalleCarrito obtener(UUID id){
        return deCaRepo.findById(id).orElse(null);
    }

    public DetalleCarrito guardar(DetalleCarrito dC){
        return deCaRepo.save(dC);
    }

    public DetalleCarrito actualizarPut(UUID detalle_carrito_id,DetalleCarrito dC){
        DetalleCarrito existente = obtener(detalle_carrito_id);
        if (existente != null) {
            existente.setId(detalle_carrito_id);
            existente.setCantidad(dC.getCantidad());
            existente.setPrecio(dC.getPrecio());
            return guardar(existente);
        }
        return null;
    }

    public void eliminar(UUID detalle_carrito_id){
        deCaRepo.deleteById(detalle_carrito_id);
    }
}
