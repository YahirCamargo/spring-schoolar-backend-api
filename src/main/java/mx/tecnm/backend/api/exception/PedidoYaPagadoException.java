package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class PedidoYaPagadoException extends RuntimeException {
    public PedidoYaPagadoException(UUID id) {
        super("El pedido con ID " + id + " ya fue pagado previamente");
    }
}
