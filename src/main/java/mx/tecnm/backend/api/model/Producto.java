package mx.tecnm.backend.api.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @OneToMany(mappedBy = "producto", orphanRemoval = true)
    @JsonManagedReference(value = "producto-detalleCarrito-ref")
    private List<DetalleCarrito> detalleCarrito;

    public Producto(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false,length = 60)
    public String nombre;

    @Column(nullable = false, precision = 7, scale = 2)
    public BigDecimal precio;

    @Column(nullable = false, length = 15, unique = true)
    public String sku;

    @Column(nullable = false, length = 15)
    public String color;

    @Column(nullable = false, length = 20)
    public String marca;

    @Column(nullable = false, length = 255)
    public String descripcion;

    @Column(nullable = false, precision = 5, scale = 1)
    public BigDecimal peso;

    @Column(nullable = false, precision = 5, scale = 1, columnDefinition = "NUMERIC(5, 1) DEFAULT 0.0")
    public BigDecimal alto;

    @Column(nullable = false, precision = 5, scale = 1, columnDefinition = "NUMERIC(5, 1) DEFAULT 0.0")
    public BigDecimal ancho;

    @Column(nullable = false, precision = 5, scale = 1, columnDefinition = "NUMERIC(5, 1) DEFAULT 0.0")
    public BigDecimal profundidad;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categorias_id", nullable = false, foreignKey = @ForeignKey(name = "fk_productos_categorias"))
    @JsonBackReference
    private Categoria categoria;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }

    public BigDecimal getAlto() { return alto; }
    public void setAlto(BigDecimal alto) { this.alto = alto; }

    public BigDecimal getAncho() { return ancho; }
    public void setAncho(BigDecimal ancho) { this.ancho = ancho; }

    public BigDecimal getProfundidad() { return profundidad; }
    public void setProfundidad(BigDecimal profundidad) { this.profundidad = profundidad; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
