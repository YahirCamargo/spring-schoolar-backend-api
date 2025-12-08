package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class MetodoPagoNoEncontradoException extends RuntimeException {
    public MetodoPagoNoEncontradoException(UUID id) {
        super("El metodo de pago con ID " + id + " no existe");
    }
}
