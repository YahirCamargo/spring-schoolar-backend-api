package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(UUID id) {
        super("La categoría con ID " + id + " no existe");
    }
}