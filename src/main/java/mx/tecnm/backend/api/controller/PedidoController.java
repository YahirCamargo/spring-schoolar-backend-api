package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import mx.tecnm.backend.api.model.Pedido;
import mx.tecnm.backend.api.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    private final PedidoService service;
    
    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pedido> listar(){
        return service.listar();
    }

    @GetMapping("/{pedido_id}")
    public Pedido obtener(@PathVariable UUID pedido_id){
        return service.obtener(pedido_id);
    }

    @PostMapping
    public Pedido guardar(@RequestBody Pedido pedido){
        return service.guardar(pedido);
    }

    @PutMapping("/{pedido_id}")
    public Pedido actualizar(@PathVariable UUID pedido_id, @RequestBody Pedido pedido){
        return service.actualizarPut(pedido_id, pedido);
    }

    @DeleteMapping("/{pedido_id}")
    public void eliminar(@PathVariable UUID pedido_id){
        service.eliminar(pedido_id);
    }
}
