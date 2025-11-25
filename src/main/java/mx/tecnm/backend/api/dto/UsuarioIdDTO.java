package mx.tecnm.backend.api.dto;

import java.util.UUID;
public class UsuarioIdDTO {
    private final UUID id;

    public UsuarioIdDTO(UUID id){
        this.id = id;
    }

    public UUID getId() { return id; }
    //public void setId(UUID id) { this.id = id; }
}
