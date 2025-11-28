package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MetodoPagoGetDTO {
    private final UUID id;
    private final String nombre;
    private final BigDecimal comision;

    public MetodoPagoGetDTO(UUID id, String nombre, BigDecimal comision){
        this.id= id;
        this.nombre = nombre;
        this.comision = comision;
    }
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getComision() { return comision; }
}
