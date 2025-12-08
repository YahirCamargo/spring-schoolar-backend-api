package mx.tecnm.backend.api.exception;

import java.util.UUID;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(UUID id) {
        super("El usuario con ID " + id + " no existe");
    }
}
