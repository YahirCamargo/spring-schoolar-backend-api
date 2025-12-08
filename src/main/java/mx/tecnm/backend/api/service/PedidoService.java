package mx.tecnm.backend.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tecnm.backend.api.dto.DetallePedidoPostDTO;
import mx.tecnm.backend.api.dto.EnvioPostDTO;
import mx.tecnm.backend.api.dto.PedidoPostDTO;
import mx.tecnm.backend.api.exception.DomicilioNoFavoritoException;
import mx.tecnm.backend.api.exception.PedidoNoEncontradoException;
import mx.tecnm.backend.api.exception.PedidoYaPagadoException;
import mx.tecnm.backend.api.exception.DetalleCarritoVacioException;
import mx.tecnm.backend.api.model.DetalleCarrito;
import mx.tecnm.backend.api.model.Pedido;
import mx.tecnm.backend.api.repository.DetalleCarritoRepository;
import mx.tecnm.backend.api.repository.DetallePedidoRepository;
import mx.tecnm.backend.api.repository.DomicilioRepository;
import mx.tecnm.backend.api.repository.EnvioRepository;
import mx.tecnm.backend.api.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final DetallePedidoRepository detallePedidoRepo;
    private final DomicilioRepository domicilioRepo;
    private final EnvioRepository envioRepo;
    private final DetalleCarritoRepository carritoRepo;

    public PedidoService(PedidoRepository pedidoRepo,DetallePedidoRepository detallePedidoRepo,EnvioRepository envioRepo,DetalleCarritoRepository carritoRepo,DomicilioRepository domicilioRepo) {
        this.pedidoRepo = pedidoRepo;
        this.detallePedidoRepo = detallePedidoRepo;
        this.envioRepo = envioRepo;
        this.carritoRepo = carritoRepo;
        this.domicilioRepo = domicilioRepo;
    }

    @Transactional
    public Pedido crearPedido(PedidoPostDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNumero(UUID.randomUUID());
        pedido.setUsuariosId(dto.getUsuariosId());
        pedido.setMetodosPagoId(dto.getMetodosPagoId());
        pedido.setImporteEnvio(dto.getImporteEnvio());
        pedido.setFechaHoraPago(null);

        List<DetalleCarrito> carrito = carritoRepo.findByUserId(dto.getUsuariosId());
        if (carrito.isEmpty())
            throw new DetalleCarritoVacioException();

        BigDecimal totalProductos = BigDecimal.ZERO;

        for (DetalleCarrito item : carrito) {
            totalProductos = totalProductos.add(item.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
        }

        //BigDecimal total = totalProductos.add(pedido.getImporteEnvio());

        pedido.setImporteProducto(totalProductos);
        //pedido.setTotal(total);

        Pedido pedidoGuardado = pedidoRepo.save(pedido);

        for (DetalleCarrito item : carrito) {
            DetallePedidoPostDTO det = new DetallePedidoPostDTO(
                    item.getCantidad(),
                    item.getPrecio(),
                    item.getProductosId(),
                    pedidoGuardado.getId()
            );
            detallePedidoRepo.save(det);
        }

        UUID idDomicilio = domicilioRepo.findPreferidoByUsuarioId(dto.getUsuariosId())
                .orElseThrow(DomicilioNoFavoritoException::new)
                .getId();

        String random = "TRK-" +String.valueOf((int)(Math.random()*900000) + 100000);
        envioRepo.save(new EnvioPostDTO(idDomicilio,pedidoGuardado.getId()),random);

        carritoRepo.clearDetallesCarrito(dto.getUsuariosId());

        return pedidoGuardado;
    }

    @Transactional
    public Pedido pagarPedido(UUID pedidoId) {
        Pedido pedido = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));

        if(pedido.getFechaHoraPago() != null)
            throw new PedidoYaPagadoException(pedidoId);

        pedidoRepo.actualizarPorPagoHecho(pedidoId);

        return pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    }
}