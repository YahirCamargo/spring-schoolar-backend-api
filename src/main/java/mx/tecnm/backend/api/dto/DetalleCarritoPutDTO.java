package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;

public class DetalleCarritoPutDTO {
    private final int cantidad;
    private final BigDecimal precio;

    public DetalleCarritoPutDTO(int cantidad, BigDecimal precio){
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getCantidad() { return cantidad; }
    public BigDecimal getPrecio() { return precio; }
}
