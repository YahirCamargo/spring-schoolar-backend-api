package mx.tecnm.backend.api.exception;

public class DomicilioNoFavoritoException extends RuntimeException {
    public DomicilioNoFavoritoException() {
        super("No hay domicilio favorito para este usuario");
    }
}
