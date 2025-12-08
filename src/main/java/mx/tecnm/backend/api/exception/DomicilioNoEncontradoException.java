package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class DomicilioNoEncontradoException extends RuntimeException {
    public DomicilioNoEncontradoException(UUID id) {
        super("El domicilio con ID " + id + " no existe");
    }
}
