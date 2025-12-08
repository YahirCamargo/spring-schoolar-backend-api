package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(UUID id) {
        super("El pedido con ID " + id + " no existe");
    }
}
