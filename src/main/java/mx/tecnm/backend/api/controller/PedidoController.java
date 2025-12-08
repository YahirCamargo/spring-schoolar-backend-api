package mx.tecnm.backend.api.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import mx.tecnm.backend.api.dto.PedidoPostDTO;
import mx.tecnm.backend.api.model.Pedido;
import mx.tecnm.backend.api.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Endpoints para la gestión de pedidos.")
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
     private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoPostDTO dto) {
        Pedido pedido = service.crearPedido(dto);
        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Pedido> pagarPedido(@PathVariable UUID id) {
        Pedido pedido = service.pagarPedido(id);
        return ResponseEntity.ok(pedido);
    }


}
