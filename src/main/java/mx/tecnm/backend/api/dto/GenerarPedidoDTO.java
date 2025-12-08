package mx.tecnm.backend.api.dto;

import java.util.UUID;

public class GenerarPedidoDTO {
    private UUID usuarioId;
    private UUID metodosPagoId;
    private UUID domicilioId;

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public UUID getMetodosPagoId() { return metodosPagoId; }
    public void setMetodosPagoId(UUID metodosPagoId) { this.metodosPagoId = metodosPagoId; }

    public UUID getDomicilioId() { return domicilioId; }
    public void setDomicilioId(UUID domicilioId) { this.domicilioId = domicilioId; }
}
