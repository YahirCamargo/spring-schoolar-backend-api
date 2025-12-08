package mx.tecnm.backend.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Envio {
    private UUID id;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fecha;
    private EstadoEnvio estado;
    private String numeroSeguimiento;
    private UUID domiciliosId;
    private UUID pedidosId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }

    public String getNumeroSeguimiento() { return numeroSeguimiento; }
    public void setNumeroSeguimiento(String numeroSeguimiento) { this.numeroSeguimiento = numeroSeguimiento; }

    public UUID getDomiciliosId() { return domiciliosId; }
    public void setDomiciliosId(UUID domiciliosId) { this.domiciliosId = domiciliosId; }

    public UUID getPedidosId() { return pedidosId; }
    public void setPedidosId(UUID pedidosId) { this.pedidosId = pedidosId; }

}
