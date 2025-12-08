package mx.tecnm.backend.api.dto;


public class DetalleCarritoPutDTO {
    private final int cantidad;


    public DetalleCarritoPutDTO(int cantidad){
        this.cantidad = cantidad;
    }

    public int getCantidad() { return cantidad; }
}
