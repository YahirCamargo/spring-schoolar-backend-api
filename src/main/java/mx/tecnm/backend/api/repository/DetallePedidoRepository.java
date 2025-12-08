package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.DetallePedido;
import mx.tecnm.backend.api.dto.DetallePedidoPostDTO; 
import mx.tecnm.backend.api.dto.DetallePedidoPutDTO; 
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class DetallePedidoRepository {
    private final JdbcClient jdbc;

    public DetallePedidoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<DetallePedido> findAll() {
        String sql = "SELECT id,cantidad,precio,productos_id,pedidos_id FROM detalles_pedido WHERE activo=TRUE";

        return jdbc.sql(sql)
                .query((rs, rowNum) -> {
                    DetallePedido dP = new DetallePedido();
                    dP.setId(rs.getObject("id", UUID.class));
                    dP.setCantidad(rs.getInt("cantidad"));
                    dP.setPrecio(rs.getBigDecimal("precio"));
                    dP.setProductosId(rs.getObject("productos_id",UUID.class));
                    dP.setPedidosId(rs.getObject("pedidos_id",UUID.class));
                    return dP;
                })
                .list();
    }

    public DetallePedido findById(UUID detallePedidoId) {
        String sql = "SELECT id,cantidad,precio,productos_id,pedidos_id FROM detalles_pedido WHERE id = :id AND activo=TRUE";

        try {
            return jdbc.sql(sql)
                    .param("id", detallePedidoId)
                    .query((rs, rowNum) -> {
                        DetallePedido dP = new DetallePedido();
                        dP.setId(rs.getObject("id", UUID.class));
                        dP.setCantidad(rs.getInt("cantidad"));
                        dP.setPrecio(rs.getBigDecimal("precio"));
                        dP.setProductosId(rs.getObject("productos_id",UUID.class));
                        dP.setPedidosId(rs.getObject("pedidos_id",UUID.class));
                        return dP;
                    })
                    .single();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public DetallePedido save(DetallePedidoPostDTO detallePedidoACrear) {
        String sql = "INSERT INTO detalles_pedido (cantidad,precio,productos_id,pedidos_id)" 
        +"VALUES (:cantidad,:precio,:productos_id,:pedidos_id) RETURNING id";

        UUID generatedId = jdbc.sql(sql)
                .param("cantidad", detallePedidoACrear.getCantidad())
                .param("precio", detallePedidoACrear.getPrecio())
                .param("productos_id", detallePedidoACrear.getProductosId())
                .param("pedidos_id", detallePedidoACrear.getPedidosId())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        DetallePedido nueva = new DetallePedido();
        nueva.setId(generatedId);
        nueva.setCantidad(detallePedidoACrear.getCantidad());
        nueva.setPrecio(detallePedidoACrear.getPrecio());
        nueva.setProductosId(detallePedidoACrear.getProductosId());
        nueva.setPedidosId(detallePedidoACrear.getPedidosId());

        return nueva;
    }


    public DetallePedido update(DetallePedidoPutDTO detallePedidoAActualizar, UUID detallePedidoId) {
        
        String sql = """
                UPDATE detalles_pedido SET
                    cantidad = :cantidad,
                    precio = :precio
                WHERE id = :id AND activo=TRUE
                """;

        int rowsAffected = jdbc.sql(sql)
                .param("cantidad", detallePedidoAActualizar.getCantidad())
                .param("precio", detallePedidoAActualizar.getPrecio())
                .param("id", detallePedidoId)
                .update();

        if (rowsAffected == 0) return null;

        return findById(detallePedidoId);
    }

    public int deactivateById(UUID id) {
        String sql = "UPDATE detalles_pedido SET activo=FALSE WHERE id = :id AND activo=TRUE";

        return jdbc.sql(sql)
                .param("id", id)
                .update();
    }
}
