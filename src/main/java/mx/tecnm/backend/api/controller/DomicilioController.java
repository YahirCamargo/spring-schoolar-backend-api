package mx.tecnm.backend.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.service.DomicilioService;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "*")

public class DomicilioController {

    private final DomicilioService service;
    
    public DomicilioController(DomicilioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Domicilio> listar(){
        return service.listar();
    }

    @GetMapping("/{domicilio_id}")
    public Domicilio obtener(@PathVariable UUID domicilio_id){
        return service.obtener(domicilio_id);
    }

    @PostMapping
    public Domicilio guardar(@RequestBody Domicilio domicilio){
        return service.guardar(domicilio);
    }

    @PutMapping("/{domicilio_id}")
    public Domicilio actualizar(@PathVariable UUID domicilio_id, @RequestBody Domicilio domicilio){
        return service.actualizarPut(domicilio_id, domicilio);
    }

    @DeleteMapping("/{domicilio_id}")
    public void eliminar(@PathVariable UUID domicilio_id){
        service.eliminar(domicilio_id);
    }

}
