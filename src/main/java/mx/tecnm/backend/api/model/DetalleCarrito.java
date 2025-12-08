package mx.tecnm.backend.api.model;

import java.math.BigDecimal;
import java.util.UUID;

public class DetalleCarrito {
    private UUID id;
    private int cantidad;
    private BigDecimal precio;
    private UUID productosId;
    private UUID usuariosId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public UUID getProductosId() { return productosId; }
    public void setProductosId(UUID productosId) {  this.productosId = productosId; }

    public UUID getUsuariosId() { return usuariosId;}
    public void setUsuariosId(UUID usuariosId) { this.usuariosId = usuariosId; }
}
