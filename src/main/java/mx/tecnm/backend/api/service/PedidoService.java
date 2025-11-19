package mx.tecnm.backend.api.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import mx.tecnm.backend.api.model.Pedido;
import mx.tecnm.backend.api.repository.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository pRepo;

    public PedidoService(PedidoRepository pRepo) {
        this.pRepo = pRepo;
    }

    public List<Pedido> listar(){
        return pRepo.findAll();
    }

    public Pedido obtener(UUID pedido_id){
        return pRepo.findById(pedido_id).orElse(null);
    }

    public Pedido guardar(Pedido p){
        return pRepo.save(p);
    }

    public Pedido actualizarPut(UUID pedido_id,Pedido p){
        Pedido existente = pRepo.findById(pedido_id).orElse(null);
        if(existente != null){
            existente.setId(pedido_id);
            existente.setNumero(p.getNumero());
            existente.setImporteProductos(p.getImporteProductos());
            existente.setImporteEnvio(p.getImporteEnvio());
            return guardar(existente);
        }
        return null;
    }

    public void eliminar(UUID pedido_id){
        pRepo.deleteById(pedido_id);
    }
}
