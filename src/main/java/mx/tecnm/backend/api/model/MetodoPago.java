package mx.tecnm.backend.api.model;

import java.util.UUID;
import java.math.BigDecimal;

public class MetodoPago {
    private UUID id;
    private String nombre;
    private BigDecimal comision;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getComision() { return comision; }
    public void setComision(BigDecimal comision) { this.comision = comision; }
}
