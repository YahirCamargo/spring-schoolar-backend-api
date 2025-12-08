package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;

public class PedidoPutDTO {
    private final BigDecimal importeProducto;

    public PedidoPutDTO(BigDecimal importeProducto){
        this.importeProducto = importeProducto;
    }

    public BigDecimal getImporteProducto() { return importeProducto; }

}
