package mx.tecnm.backend.api.model;

import java.util.UUID;
public class Categoria {
    private UUID id;
    private String nombre;
    private boolean activo;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo =  activo; }
}
