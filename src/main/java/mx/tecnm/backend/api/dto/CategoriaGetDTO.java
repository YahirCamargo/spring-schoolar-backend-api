package mx.tecnm.backend.api.dto;

import java.util.UUID;

public class CategoriaGetDTO {
    private final UUID id;
    private final String nombre;

    public CategoriaGetDTO(UUID id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
}
