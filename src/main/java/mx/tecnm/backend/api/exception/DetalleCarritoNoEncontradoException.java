package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class DetalleCarritoNoEncontradoException extends RuntimeException {
    public DetalleCarritoNoEncontradoException(UUID id) {
        super("El detalle carrito con ID " + id + " no existe");
    }
}
