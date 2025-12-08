package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PedidoPostDTO {
    private final BigDecimal importeEnvio;
    private final UUID usuariosId;
    private final UUID metodosPagoId;

    public PedidoPostDTO(BigDecimal importeEnvio,UUID usuariosId,UUID metodosPagoId){
        this.importeEnvio = importeEnvio;
        this.usuariosId = usuariosId;
        this.metodosPagoId = metodosPagoId;
    }

    public BigDecimal getImporteEnvio() { return importeEnvio; }
    public UUID getUsuariosId() { return usuariosId; }
    public UUID getMetodosPagoId() { return metodosPagoId; }
    
}
