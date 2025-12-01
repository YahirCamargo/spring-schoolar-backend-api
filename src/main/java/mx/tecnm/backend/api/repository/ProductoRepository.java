package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Producto;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class ProductoRepository {
    private final JdbcClient jdbc;

    public ProductoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }
     String sql = "SELECT id,nombre,precio,sku,color,marca,descripcion,peso,alto,ancho,profundidad FROM productos WHERE activo=TRUE";

            return jdbc.sql(sql)
                .query((rs, rowNum) -> {
                Producto p = new Producto();

                p.setId(rs.getObject("id",UUID.class));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setSku(rs.getString("sku"));
                p.setColor(rs.getString("color"));
                p.setMarca(rs.getString("marca"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPeso(rs.getBigDecimal("peso"));
                p.setAlto(rs.getBigDecimal("alto"));
                p.setAncho(rs.getBigDecimal("ancho"));
                 p.setProfundidad(rs.getBigDecimal("profundidad"));
                return p;
            }).list();
    }

    public Producto findById(UUID producto_id) {
        String sql = "SELECT id,nombre,precio,sku,color,marca,descripcion,peso,alto,ancho,profundidad FROM productos WHERE activo=TRUE AND id = :id";
        
        try {
            return jdbc.sql(sql)
                .param("id",producto_id)
                .query((rs, rowNum) -> {
                    Producto p = new Producto();
                    p.setId(rs.getObject("id",UUID.class));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setSku(rs.getString("sku"));
                p.setColor(rs.getString("color"));
                p.setMarca(rs.getString("marca"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPeso(rs.getBigDecimal("peso"));
                p.setAlto(rs.getBigDecimal("alto"));
                p.setAncho(rs.getBigDecimal("ancho"));
                 p.setProfundidad(rs.getBigDecimal("profundidad"));
                    return p;
                }).single();
        }catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public Producto save(ProductoPostDTO productoACrear) {
        String sql = """
            INSERT INTO productos (nombre, precio, sku, color, marca, descripcion, peso, alto, ancho, profundidad)
            VALUES (:nombre, :precio, :sku, :color, :marca, :descripcion, :peso, :alto, :ancho, :profundidad)
            RETURNING id
            """;

        UUID generatedId = jdbc.sql(sql)
                .param("nombre", productoACrear.getNombre())
                .param("precio", productoACrear.getPrecio())
                .param("sku", productoACrear.getSku())
                .param("color", productoACrear.getColor())
                .param("marca", productoACrear.getMarca()))
                .param("descripcion", productoACrear.getDescripcion())
                .param("peso", productoACrear.getPeso())
                .param("alto", productoACrear.getAlto())
                .param("ancho", productoACrear.getAncho())
                .param("profundidad", productoACrear.getProfundidad())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        Producto nuevo = new Producto();
        nuevo.setId(generatedId);
        nuevo.setNombre(productoACrear.getNombre());
        nuevo.setPrecio(productoACrear.getPrecio());
        nuevo.setSku(productoACrear.getSku());
        nuevo.setColor(productoACrear.getColor());
        nuevo.setMarca(productoACrear.getMarca());
        nuevo.setDescripcion(productoACrear.getDescripcion());
        nuevo.setPeso(productoACrear.getPeso());
        nuevo.setAlto(productoACrear.getAlto());
        nuevo.setAncho(productoACrear.getAncho());
        nuevo.setProfundidad(productoACrear.getProfundidad());
        return nuevo;
    }


    public Producto update(ProductoPutDTO producto) {
        String sql = """
            UPDATE productos SET
                nombre = :nombre,
                precio = :precio,
                sku = :sku,
                color = :color,
                marca = :marca,
                descripcion = :descripcion,
                peso = :peso,
                alto = :alto,
                ancho = :ancho,
                profundidad = :profundidad,
            WHERE id = :id
            """;

        int rows = jdbc.sql(sql)
                .param("nombre", productoACrear.getNombre())
                .param("precio", productoACrear.getPrecio())
                .param("sku", productoACrear.getSku())
                .param("color", productoACrear.getColor())
                .param("marca", productoACrear.getMarca())
                .param("descripcion", productoACrear.getDescripcion())
                .param("peso", productoACrear.getPeso())
                .param("alto", productoACrear.getAlto())
                .param("ancho", productoACrear.getAncho())
                .param("profundidad", productoACrear.getProfundidad())
                .param("id", producto.getId())
                .update();

        if (rows == 0) {
            return null;
        }

        return this.findById(producto.getId());
    }


    public int deactivateById(UUID producto_id) {
        String sql = "UPDATE productos SET activo=FALSE WHERE id = :id";
        return jdbc.sql(sql)
            .param("id",producto_id)
            .update();
    }
}