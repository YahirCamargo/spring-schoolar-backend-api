package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.service.MetodoPagoService;

@RestController
@RequestMapping("/api/payment-method")
@CrossOrigin(origins = "*")
public class MetodoPagoController {
    private final MetodoPagoService service;

    public MetodoPagoController(MetodoPagoService service) {
        this.service = service;
    }

    @GetMapping
    public List<MetodoPago> listar(){
        return service.listar();
    }

    @GetMapping("/{metodo_pago_id}")
    public MetodoPago obtener(@PathVariable UUID metodo_pago_id){
        return service.obtener(metodo_pago_id);
    }

    @PostMapping
    public MetodoPago guardar(@RequestBody MetodoPago metodoPago){
        return service.guardar(metodoPago);
    }

    @PutMapping("/{metodo_pago_id}")
    public MetodoPago actualizar(@PathVariable UUID metodo_pago_id, @RequestBody MetodoPago metodoPago){
        return service.actualizarPut(metodo_pago_id, metodoPago);
    }

    @DeleteMapping("/{metodo_pago_id}")
    public void eliminar(@PathVariable UUID metodo_pago_id){
        service.eliminar(metodo_pago_id);
    }
}