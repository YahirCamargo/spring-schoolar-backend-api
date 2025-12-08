package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(UUID id) {
        super("El producto con ID " + id + " no existe");
    }
}
