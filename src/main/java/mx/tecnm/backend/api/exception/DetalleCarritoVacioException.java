package mx.tecnm.backend.api.exception;


public class DetalleCarritoVacioException extends RuntimeException{
    public DetalleCarritoVacioException() {
        super("El detalle carrito esta vacio para este usuario");
    }
}
