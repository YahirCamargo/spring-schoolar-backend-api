package mx.tecnm.backend.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductoPostDTO {
    private final String nombre;  
    private final BigDecimal precio;
    private final String sku; 
    private final String color;
    private final String marca;
    private final String descripcion; 
    private final BigDecimal peso;
    private final BigDecimal alto;
    private final BigDecimal ancho;
    private final BigDecimal profundidad;
    private final UUID categoriasId;

    public ProductoPostDTO(String nombre, BigDecimal precio, String sku, String color, String marca, String descripcion, BigDecimal peso, BigDecimal alto, BigDecimal ancho, BigDecimal profundidad, UUID categoriasId){
            this.nombre = nombre;
            this.precio = precio;
            this.sku = sku;
            this.color = color;
            this.marca = marca;
            this.descripcion = descripcion;
            this.peso = peso;
            this.alto = alto;
            this.ancho = ancho;
            this.profundidad = profundidad;
            this.categoriasId  = categoriasId;
    }
    
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public String getSku() { return sku; }
    public String getColor() { return color; }
    public String getMarca() { return marca; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPeso() { return peso; }
    public BigDecimal getAlto() { return alto; }
    public BigDecimal getAncho() { return ancho; }
    public BigDecimal getProfundidad() { return profundidad; }
    public UUID getCategoriasId() { return categoriasId; }

}
