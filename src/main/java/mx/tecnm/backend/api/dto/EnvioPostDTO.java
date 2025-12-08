package mx.tecnm.backend.api.dto;

import java.util.UUID;

public class EnvioPostDTO {
    private final UUID domiciliosId;
    private final UUID pedidosId;

    public EnvioPostDTO(UUID domiciliosId, UUID pedidosId){
        this.domiciliosId = domiciliosId;
        this.pedidosId = pedidosId;
    }

    public UUID getDomiciliosId() { return domiciliosId; }
    public UUID getPedidosId() { return pedidosId; }
}
