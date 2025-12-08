package mx.tecnm.backend.api.repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import mx.tecnm.backend.api.model.Pedido;

@Repository
public class PedidoRepository {
    private final JdbcClient jdbc;

    public PedidoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public Pedido save(Pedido pedido) {
        String sql = """
            INSERT INTO pedidos (numero, importe_productos, importe_envio,
                                usuarios_id, metodos_pago_id, fecha_hora_pago)
            VALUES (:numero, :importe_productos, :importe_envio,
                    :usuarios_id, :metodos_pago_id, :fecha_hora_pago)
            RETURNING id, fecha, numero, importe_productos, importe_envio,
                      importe_iva, total, usuarios_id, metodos_pago_id, fecha_hora_pago, activo
        """;

        return jdbc.sql(sql)
                .param("numero", UUID.randomUUID())
                .param("importe_productos", pedido.getImporteProducto())
                .param("importe_envio", pedido.getImporteEnvio())
                .param("usuarios_id", pedido.getUsuariosId())
                .param("metodos_pago_id", pedido.getMetodosPagoId())
                .param("fecha_hora_pago", pedido.getFechaHoraPago())
                .query((rs, rowNum) -> {
                    Pedido p = new Pedido();
                    p.setId(rs.getObject("id", UUID.class));
                    p.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    p.setNumero(rs.getObject("numero", UUID.class));
                    p.setImporteProducto(rs.getBigDecimal("importe_productos"));
                    p.setImporteEnvio(rs.getBigDecimal("importe_envio"));
                    p.setImporteIva(rs.getBigDecimal("importe_iva"));
                    p.setTotal(rs.getBigDecimal("total"));
                    p.setUsuariosId(rs.getObject("usuarios_id", UUID.class));
                    p.setMetodosPagoId(rs.getObject("metodos_pago_id", UUID.class));
                    p.setFechaHoraPago(rs.getTimestamp("fecha_hora_pago") != null 
                            ? rs.getTimestamp("fecha_hora_pago").toLocalDateTime() : null);
                    return p;
                })
                .single();
    }

    public Optional<Pedido> findById(UUID id){
        String sql = """
                SELECT id, fecha, numero, importe_productos, importe_envio, importe_iva, total,
                       usuarios_id, metodos_pago_id, fecha_hora_pago
                FROM pedidos
                WHERE id=:id AND activo=TRUE
        """;

        try {
            Pedido pedido = jdbc.sql(sql)
                    .param("id", id)
                    .query((rs,row)->{
                        Pedido p = new Pedido();
                        p.setId(rs.getObject("id",UUID.class));
                        p.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                        p.setNumero(rs.getObject("numero",UUID.class));
                        p.setImporteProducto(rs.getBigDecimal("importe_productos"));
                        p.setImporteEnvio(rs.getBigDecimal("importe_envio"));
                        p.setImporteIva(rs.getBigDecimal("importe_iva"));
                        p.setTotal(rs.getBigDecimal("total"));
                        p.setUsuariosId(rs.getObject("usuarios_id",UUID.class));
                        p.setMetodosPagoId(rs.getObject("metodos_pago_id",UUID.class));
                        p.setFechaHoraPago(rs.getTimestamp("fecha_hora_pago") != null 
                                ? rs.getTimestamp("fecha_hora_pago").toLocalDateTime() : null);
                        return p;
                    })
                    .single();
            return Optional.of(pedido);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    public int actualizarImporte(BigDecimal importeProducto, UUID id){
        String sql = "UPDATE pedidos SET importe_productos=:importe WHERE id=:id AND activo = TRUE";

        return jdbc.sql(sql)
                .param("importe",importeProducto)
                .param("id", id)
                .update();
    }


    public int actualizarPorPagoHecho(UUID id){
        String sql = "UPDATE pedidos SET fecha_hora_pago=now() WHERE id=:id AND activo = TRUE";

        return jdbc.sql(sql)
                .param("id",id)
                .update();
    }
    
    public int delete(UUID id){
        String sql = "UPDATE pedidos SET activo=FALSE WHERE id=:id AND activo = TRUE";

        return jdbc.sql(sql)
                .param("id", id)
                .update();
    }
}
