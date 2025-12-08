package mx.tecnm.backend.api.handler;

import mx.tecnm.backend.api.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoriaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleCategoriaNoEncontrada(CategoriaNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "CATEGORIA_NO_ENCONTRADA"));
    }

    @ExceptionHandler(DetalleCarritoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleDetalleCarritoNoEncontrado(DetalleCarritoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "CARRITO_NO_ENCONTRADO"));
    }

    @ExceptionHandler(DetalleCarritoVacioException.class)
    public ResponseEntity<Map<String, Object>> handleCarritoVacio(DetalleCarritoVacioException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "CARRITO_VACIO"));
    }

    @ExceptionHandler(DomicilioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleDomicilioNoEncontrada(DomicilioNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "DOMICILIO_NO_ENCONTRADO"));
    }

    @ExceptionHandler(DomicilioNoFavoritoException.class)
    public ResponseEntity<Map<String, Object>> handleDomicilioNoFavorito(DomicilioNoFavoritoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "DOMICILIO_FAVORITO_NO_SELECCIONADO"));
    }

    @ExceptionHandler(MetodoPagoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleMetodoPagoNoEncontrado(MetodoPagoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "METODO_DE_PAGO_NO_ENCONTRADO"));
    }

    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handlePedidoNoEncontrado(PedidoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "PEDIDO_NO_ENCONTRADO"));
    }

    @ExceptionHandler(PedidoYaPagadoException.class)
    public ResponseEntity<Map<String, Object>> handlePedidoYaPagado(PedidoYaPagadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse(ex.getMessage(), "PEDIDO_YA_PAGADO"));
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleProductoNoEncontrado(ProductoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "PRODUCTO_NO_ENCONTRADO"));
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse(ex.getMessage(), "USUARIO_NO_ENCONTRADO"));
    }

    private Map<String, Object> errorResponse(String mensaje, String codigo) {
        return Map.of(
            "error", mensaje,
            "codigo", codigo,
            "timestamp", new Date()
        );
    }
}
