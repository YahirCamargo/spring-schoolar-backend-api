package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Envio;
import mx.tecnm.backend.api.model.EstadoEnvio;
import mx.tecnm.backend.api.dto.EnvioPostDTO;
import mx.tecnm.backend.api.dto.EnvioPutDTO;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class EnvioRepository {
    private final JdbcClient jdbc;

    public EnvioRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Envio> findAll() {
        String sql = "SELECT id,fecha_entrega,fecha,estado,numero_seguimiento,domicilios_id,pedidos_id FROM envios WHERE activo=TRUE";

        return jdbc.sql(sql)
                .query((rs, rowNum) -> {
                    Envio e = new Envio();
                    e.setId(rs.getObject("id", UUID.class));
                    e.setFechaEntrega(rs.getObject("fecha_entrega", LocalDateTime.class));
                    e.setFecha(rs.getObject("fecha", LocalDateTime.class));
                    e.setEstado(EstadoEnvio.valueOf(rs.getString("estado")));
                    e.setNumeroSeguimiento(rs.getString("numero_seguimiento"));
                    e.setDomiciliosId(rs.getObject("domicilios_id",UUID.class));
                    e.setPedidosId(rs.getObject("pedidos_id",UUID.class));
                    return e;
                })
                .list();
    }

    public Envio findById(UUID envioId) {
        String sql = "SELECT id,fecha_entrega,fecha,estado,numero_seguimiento,domicilios_id,pedidos_id FROM envios WHERE id = :id AND activo=TRUE";

        try {
            return jdbc.sql(sql)
                    .param("id", envioId)
                    .query((rs, rowNum) -> {
                        Envio e = new Envio();
                        e.setId(rs.getObject("id", UUID.class));
                        e.setFecha(rs.getObject("fecha", LocalDateTime.class));
                        e.setFechaEntrega(rs.getObject("fecha_entrega", LocalDateTime.class));
                        e.setEstado(EstadoEnvio.valueOf(rs.getString("estado")));
                        e.setNumeroSeguimiento(rs.getString("numero_seguimiento"));
                        e.setDomiciliosId(rs.getObject("domicilios_id",UUID.class));
                        e.setPedidosId(rs.getObject("pedidos_id",UUID.class));
                        return e;
                    })
                    .single();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Envio save(EnvioPostDTO envioACrear,String numeroSeguimiento) {
        String sql = "INSERT INTO envios (numero_seguimiento,domicilios_id,pedidos_id)" 
        +"VALUES (:numero_seguimiento,:domicilios_id,:pedidos_id) RETURNING id";

        UUID generatedId = jdbc.sql(sql)
                .param("numero_seguimiento", numeroSeguimiento)
                .param("domicilios_id", envioACrear.getDomiciliosId())
                .param("pedidos_id", envioACrear.getPedidosId())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        Envio nueva = new Envio();
        nueva.setId(generatedId);
        nueva.setNumeroSeguimiento(numeroSeguimiento);
        nueva.setDomiciliosId(envioACrear.getDomiciliosId());
        nueva.setPedidosId(envioACrear.getPedidosId());

        return nueva;
    }


    public Envio update(EnvioPutDTO envioAActualizar, UUID envioId) {
        
        String sql = """
                UPDATE envios SET
                    fecha_entrega = :fecha_entrega,
                    estado = :estado
                WHERE id = :id AND activo=TRUE
                """;

        int rowsAffected = jdbc.sql(sql)
                .param("fecha_entrega", envioAActualizar.getFechaEntrega())
                .param("estado", envioAActualizar.getEstado())
                .param("id", envioId)
                .update();

        if (rowsAffected == 0) return null;

        return findById(envioId);
    }

    public int deactivateById(UUID id) {
        String sql = "UPDATE envios SET activo=FALSE WHERE id = :id AND activo=TRUE";

        return jdbc.sql(sql)
                .param("id", id)
                .update();
    }
}
