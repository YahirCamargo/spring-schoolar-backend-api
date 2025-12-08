package mx.tecnm.backend.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pedido {
    private UUID id;
    private LocalDateTime fecha;
    private UUID numero;
    private BigDecimal importeProducto;
    private BigDecimal importeEnvio;
    private BigDecimal importeIva;
    private BigDecimal total;
    private UUID usuariosId;
    private UUID metodosPagoId;
    private LocalDateTime fechaHoraPago;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public UUID getNumero() { return numero; }
    public void setNumero(UUID numero) { this.numero = numero; }

    public BigDecimal getImporteProducto() { return importeProducto; }
    public void setImporteProducto(BigDecimal importeProducto) { this.importeProducto = importeProducto; }

    public BigDecimal getImporteIva() { return importeIva; }
    public void setImporteIva(BigDecimal importeIva) { this.importeIva = importeIva; }

    public BigDecimal getImporteEnvio() { return importeEnvio; }
    public void setImporteEnvio(BigDecimal importeEnvio) { this.importeEnvio = importeEnvio; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }


    public UUID getUsuariosId() { return usuariosId; }
    public void setUsuariosId(UUID usuariosId) { this.usuariosId = usuariosId; }

    public UUID getMetodosPagoId() { return metodosPagoId; }
    public void setMetodosPagoId(UUID metodosPagoId) { this.metodosPagoId = metodosPagoId; }

    public LocalDateTime getFechaHoraPago() { return fechaHoraPago; }
    public void setFechaHoraPago(LocalDateTime fechaHoraPago) { this.fechaHoraPago = fechaHoraPago; }

}
