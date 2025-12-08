package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.DetalleCarrito;
import mx.tecnm.backend.api.dto.DetalleCarritoPostDTO; 
import mx.tecnm.backend.api.dto.DetalleCarritoPutDTO; 
import java.util.UUID;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class DetalleCarritoRepository {
    private final JdbcClient jdbc;

    public DetalleCarritoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<DetalleCarrito> findAll() {
        String sql = "SELECT id,cantidad,precio,productos_id,usuarios_id FROM detalles_carrito WHERE activo=TRUE";

        return jdbc.sql(sql)
                .query((rs, rowNum) -> {
                    DetalleCarrito dC = new DetalleCarrito();
                    dC.setId(rs.getObject("id", UUID.class));
                    dC.setCantidad(rs.getInt("cantidad"));
                    dC.setPrecio(rs.getBigDecimal("precio"));
                    dC.setProductosId(rs.getObject("productos_id",UUID.class));
                    dC.setUsuariosId(rs.getObject("usuarios_id",UUID.class));
                    return dC;
                })
                .list();
    }

    public Optional<DetalleCarrito> findById(UUID detalleCarritoId) {
        String sql = "SELECT id,cantidad,precio,productos_id,usuarios_id FROM detalles_carrito WHERE id = :id AND activo=TRUE";

        try {
            DetalleCarrito detalleCarrito = jdbc.sql(sql)
                    .param("id", detalleCarritoId)
                    .query((rs, rowNum) -> {
                        DetalleCarrito dC = new DetalleCarrito();
                        dC.setId(rs.getObject("id", UUID.class));
                        dC.setCantidad(rs.getInt("cantidad"));
                        dC.setPrecio(rs.getBigDecimal("precio"));
                        dC.setProductosId(rs.getObject("productos_id",UUID.class));
                        dC.setUsuariosId(rs.getObject("usuarios_id",UUID.class));
                        return dC;
                    })
                    .single();
            return Optional.of(detalleCarrito);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<DetalleCarrito> findByUserId(UUID usuarioId) {
        String sql = "SELECT id,cantidad,precio,productos_id,usuarios_id FROM detalles_carrito WHERE usuarios_id = :usuarios_id AND activo=TRUE";

            return jdbc.sql(sql)
                    .param("usuarios_id", usuarioId)
                    .query((rs, rowNum) -> {
                        DetalleCarrito dC = new DetalleCarrito();
                        dC.setId(rs.getObject("id", UUID.class));
                        dC.setCantidad(rs.getInt("cantidad"));
                        dC.setPrecio(rs.getBigDecimal("precio"));
                        dC.setProductosId(rs.getObject("productos_id",UUID.class));
                        dC.setUsuariosId(usuarioId);
                        return dC;
                    })
                    .list();
    }
    
    public DetalleCarrito save(DetalleCarritoPostDTO detalleCarritoACrear, BigDecimal precio) {
        String sql = "INSERT INTO detalles_carrito (cantidad,precio,productos_id,usuarios_id)" 
        +"VALUES (:cantidad,:precio,:productos_id,:usuarios_id) RETURNING id";

        UUID generatedId = jdbc.sql(sql)
                .param("cantidad", detalleCarritoACrear.getCantidad())
                .param("precio", precio)
                .param("productos_id", detalleCarritoACrear.getProductosId())
                .param("usuarios_id", detalleCarritoACrear.getUsuariosId())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        DetalleCarrito nueva = new DetalleCarrito();
        nueva.setId(generatedId);
        nueva.setCantidad(detalleCarritoACrear.getCantidad());
        nueva.setPrecio(precio);
        nueva.setProductosId(detalleCarritoACrear.getProductosId());
        nueva.setUsuariosId(detalleCarritoACrear.getUsuariosId());

        return nueva;
    }


    public Optional<DetalleCarrito> update(DetalleCarritoPutDTO detalleCarritoACrear, UUID detalleCarritoId) {
        
        String sql = """
                UPDATE detalles_carrito SET
                    cantidad = :cantidad
                WHERE id = :id AND activo=TRUE
                """;

        int rowsAffected = jdbc.sql(sql)
                .param("cantidad", detalleCarritoACrear.getCantidad())
                .param("id", detalleCarritoId)
                .update();

        if (rowsAffected == 0) Optional.empty();

        return findById(detalleCarritoId);
    }

    public DetalleCarrito detalle_carrito_exists(UUID usuarioId, UUID productoId){
        String sql = "SELECT id,cantidad,precio,productos_id,usuarios_id FROM detalles_carrito WHERE usuarios_id=:usuarios_id AND productos_id=:productos_id AND activo=TRUE";

        try{
            DetalleCarrito detalleCarrito = new DetalleCarrito();
            detalleCarrito=jdbc.sql(sql)
            .param("usuarios_id",usuarioId)
            .param("productos_id",productoId)
                    .query((rs, rowNum) -> {
                        DetalleCarrito dC = new DetalleCarrito();
                        dC.setId(rs.getObject("id", UUID.class));
                        dC.setCantidad(rs.getInt("cantidad"));
                        dC.setPrecio(rs.getBigDecimal("precio"));
                        dC.setProductosId(productoId);
                        dC.setUsuariosId(usuarioId);
                        return dC;
                    })
                    .single();

            return detalleCarrito;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deactivateById(UUID id) {
        String sql = "UPDATE detalles_carrito SET activo=FALSE WHERE id = :id AND activo=TRUE";

        return jdbc.sql(sql)
                .param("id", id)
                .update();
    }

    public int clearDetallesCarrito(UUID usuarioId){
        String sql = "UPDATE detalles_carrito SET activo=FALSE WHERE usuarios_id=:usuarios_id";

        return jdbc.sql(sql)
            .param("usuarios_id",usuarioId)
            .update();
    }
}
