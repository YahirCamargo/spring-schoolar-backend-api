package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class DetallePedidoPostDTO {
    private final int cantidad;
    private final BigDecimal precio;
    private final UUID productosId;
    private final UUID pedidosId;

    public DetallePedidoPostDTO(int cantidad, BigDecimal precio, UUID productosId, UUID pedidosId){
        this.cantidad = cantidad;
        this.precio = precio;
        this.productosId = productosId;
        this.pedidosId = pedidosId;
    }

    public int getCantidad() { return cantidad; }
    public BigDecimal getPrecio() { return precio; }
    public UUID getProductosId() { return productosId; }
    public UUID getPedidosId() { return pedidosId; }
}
