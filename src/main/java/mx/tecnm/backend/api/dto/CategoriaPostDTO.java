package mx.tecnm.backend.api.dto;

public class CategoriaPostDTO {
    private final String nombre;

    public CategoriaPostDTO(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
}
