package mx.tecnm.backend.api.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "pedidos")
public class Pedido {
    public Pedido(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, unique = true)
    @ColumnDefault("gen_random_uuid()")
    @Generated(GenerationTime.INSERT)
    private UUID numero;

    @Column(name = "importe_productos",nullable = false, precision = 10, scale = 2)
    private BigDecimal importeProductos;

    @Column(name = "importe_envio",nullable = false, precision = 6, scale = 2)
    private BigDecimal importeEnvio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedidos_usuarios"))
    @JsonBackReference(value = "usuario-pedido-ref")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metodos_pago_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedidos_metodos_pago"))
    @JsonBackReference(value = "metodoPago-pedido-ref")
    private MetodoPago metodoPago;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getNumero() { return numero; }
    public void setNumero(UUID numero) { this.numero = numero; }

    public BigDecimal getImporteProductos() { return importeProductos; }
    public void setImporteProductos(BigDecimal importeProductos) { this.importeProductos = importeProductos; }

    public BigDecimal getImporteEnvio() { return importeEnvio; }
    public void setImporteEnvio(BigDecimal importeEnvio) { this.importeEnvio = importeEnvio; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
}
