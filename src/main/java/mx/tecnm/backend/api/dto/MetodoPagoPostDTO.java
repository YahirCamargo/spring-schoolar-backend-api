package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;
public class MetodoPagoPostDTO {
    private final String nombre;
    private final BigDecimal comision;

    public MetodoPagoPostDTO(String nombre, BigDecimal comision){
        this.nombre = nombre;
        this.comision = comision;
    }

    public String getNombre() { return nombre; }
    public BigDecimal getComision() { return comision; }
}
