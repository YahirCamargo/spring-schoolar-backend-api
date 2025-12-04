package mx.tecnm.backend.api.model;

import java.util.UUID;
import java.math.BigDecimal;
public class Producto {
    private UUID id;
    private String nombre;  
    private BigDecimal precio;
    private String sku; 
    private String color;
    private String marca;
    private String descripcion; 
    private BigDecimal peso;
    private BigDecimal alto;
    private BigDecimal ancho;
    private BigDecimal profundidad;
    private UUID categoriasId;
    private boolean activo;

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

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo =  activo; }

    public UUID getCategoriasId() { return categoriasId; }
    public void setCategoriasId(UUID categoriasId) { this.categoriasId = categoriasId; }
    
}