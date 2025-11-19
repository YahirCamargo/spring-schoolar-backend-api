package mx.tecnm.backend.api.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "detalles_carrito")
public class DetalleCarrito {
    public DetalleCarrito(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, columnDefinition = "DEFAULT 1")
    private int cantidad;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false, foreignKey = @ForeignKey(name = "fk_detalles_carrito_usuarios"))
    @JsonBackReference(value = "usuario-detalleCarrito-ref")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productos_id", nullable = false, foreignKey = @ForeignKey(name = "fk_detalles_carrito_producto"))
    @JsonBackReference(value = "producto-detalleCarrito-ref")
    private Producto producto;  
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}
