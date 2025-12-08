package mx.tecnm.backend.api.dto;

import java.util.UUID;

public class DetalleCarritoPostDTO {
    private final int cantidad;
    private final UUID productosId;
    private final UUID usuariosId;

    public DetalleCarritoPostDTO(int cantidad, UUID productosId, UUID usuariosId){
        this.cantidad = cantidad;
        this.productosId = productosId;
        this.usuariosId = usuariosId;
    }

    public int getCantidad() { return cantidad; }
    public UUID getProductosId() { return productosId; }
    public UUID getUsuariosId() { return usuariosId; }
}
