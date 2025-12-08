package mx.tecnm.backend.api.dto;

import java.time.LocalDateTime;
import mx.tecnm.backend.api.model.EstadoEnvio;

public class EnvioPutDTO {
    private final LocalDateTime fechaEntrega;
    private final EstadoEnvio estado;

    public EnvioPutDTO(LocalDateTime fechaEntrega,EstadoEnvio estado){
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public EstadoEnvio getEstado() { return estado; }
}
