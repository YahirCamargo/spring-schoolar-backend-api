package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.DetalleCarrito;
import mx.tecnm.backend.api.dto.DetalleCarritoPostDTO;
import mx.tecnm.backend.api.dto.DetalleCarritoPutDTO;
import mx.tecnm.backend.api.exception.DetalleCarritoNoEncontradoException;
import mx.tecnm.backend.api.exception.ProductoNoEncontradoException;
import mx.tecnm.backend.api.exception.UsuarioNoEncontradoException;
import mx.tecnm.backend.api.repository.DetalleCarritoRepository;
import mx.tecnm.backend.api.repository.ProductoRepository;
import mx.tecnm.backend.api.repository.UsuarioRepository;

@Service
public class DetalleCarritoService {
    private final DetalleCarritoRepository dCRepo;
    private final ProductoRepository pRepo;
    private final UsuarioRepository uRepo;

    public DetalleCarritoService(DetalleCarritoRepository dCRepo, ProductoRepository pRepo, UsuarioRepository uRepo) {
        this.dCRepo = dCRepo;
        this.pRepo = pRepo;
        this.uRepo = uRepo;
    }

    public List<DetalleCarrito> listar() {
        return dCRepo.findAll()
            .stream()
            .toList();
    }

    public DetalleCarrito obtenerPorId(UUID detalleCarritoId) {
        return dCRepo.findById(detalleCarritoId)
                    .orElseThrow(() -> new DetalleCarritoNoEncontradoException(detalleCarritoId));
    }

    public DetalleCarrito guardar(DetalleCarritoPostDTO detalleCarritoACrear){
        uRepo.findById(detalleCarritoACrear.getUsuariosId())
                    .orElseThrow(() -> new UsuarioNoEncontradoException(detalleCarritoACrear.getUsuariosId()));

        BigDecimal precioProducto = pRepo.findPrecioById(detalleCarritoACrear.getProductosId())
                    .orElseThrow(() -> new ProductoNoEncontradoException(detalleCarritoACrear.getProductosId()));

        return dCRepo.save(detalleCarritoACrear,precioProducto);
    }

    public DetalleCarrito actualizarPut(DetalleCarritoPutDTO detalleCarritoAActualizar, UUID detalleCarritoId){
        return dCRepo.update(detalleCarritoAActualizar, detalleCarritoId)
                        .orElseThrow(() -> new DetalleCarritoNoEncontradoException(detalleCarritoId));
    }

    public void eliminar(UUID detalleCarritoId){
        int rows = dCRepo.deactivateById(detalleCarritoId);
        if(rows == 0){
            throw new DetalleCarritoNoEncontradoException(detalleCarritoId);
        }
    }
}
