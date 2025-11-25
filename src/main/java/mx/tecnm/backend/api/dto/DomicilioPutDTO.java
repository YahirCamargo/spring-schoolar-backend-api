package mx.tecnm.backend.api.dto;

import java.util.UUID;
public class DomicilioPutDTO {
    private final UUID id;
    private final String calle;
    private final String numero;
    private final String colonia;
    private final String cp;
    private final String estado;
    private final String ciudad;
    private final boolean preferido;

    public DomicilioPutDTO(UUID id, String calle, String numero, String colonia, String cp, String estado, String ciudad, boolean preferido){
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.cp = cp;
        this.estado = estado;
        this.ciudad = ciudad;
        this.preferido = preferido;
    }

    public UUID getId() { return id; }
    public String getCalle() { return calle; }
    public String getNumero() { return numero; }
    public String getColonia() { return colonia; }
    public String getCp() { return cp; }
    public String getEstado() { return estado; }
    public String getCiudad() { return ciudad; }
    public boolean isPreferido() { return preferido; }
}
