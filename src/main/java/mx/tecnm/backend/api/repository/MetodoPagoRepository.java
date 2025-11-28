package mx.tecnm.backend.api.repository;


import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import mx.tecnm.backend.api.dto.MetodoPagoGetDTO;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.simple.JdbcClient;


@Repository
public class MetodoPagoRepository {
    private final JdbcClient jdbc;

    public MetodoPagoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }
    public List<MetodoPago> findAll() {
        String sql = "SELECT id,nombre,comision, activo FROM metodos_pago WHERE activo= TRUE";

        return jdbc.sql(sql)
                        .query((rs, rowNum) -> {
                            MetodoPago mP = new MetodoPago();
                            mP.setId(rs.getObject("id", UUID.class));
                            mP.setNombre(rs.getString("nombre"));
                            mP.setComision(rs.getBigDecimal("comision"));
                            mP.setActivo(rs.getBoolean("activo"));
                            return mP;
                        })
                        .list();

    }

    public MetodoPago findById(UUID metodo_pago_id) {
        String sql = "SELECT id,nombre,comision,activo FROM metodos_pago WHERE id = :id AND activo = TRUE";
    
        try {
            return jdbc.sql(sql).param("id",metodo_pago_id)
                        .query((rs, rowNum) -> {
                            MetodoPago mP = new MetodoPago();
                            mP.setId(rs.getObject("id", UUID.class));
                            mP.setNombre(rs.getString("nombre"));
                            mP.setComision(rs.getBigDecimal("comision"));
                            mP.setActivo(rs.getBoolean("activo"));
                            return mP;
                        })
                        .single();
            
        } catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public MetodoPago save(MetodoPagoPostDTO metodoPagoACrear) {
        String sql = "INSERT INTO metodos_pago (nombre,comision) VALUES (:nombre,:comision) RETURNING id";
        
        UUID generatedId = jdbc.sql(sql)
                .param("nombre", metodoPagoACrear.getNombre())
                .param("comision", metodoPagoACrear.getComision())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        MetodoPago nueva = new MetodoPago();
        nueva.setId(generatedId);
        nueva.setNombre(metodoPagoACrear.getNombre());
        nueva.setComision(metodoPagoACrear.getComision());

        return nueva;
    }

    public MetodoPago update(MetodoPagoGetDTO metodoPago) {
        
        String sql = "UPDATE metodos_pago SET "
                + "nombre = :nombre, "
                + "comision = :comision "
                + "WHERE id = :id";

        int rowsAffected = jdbc.sql(sql)
                .param("nombre", metodoPago.getNombre())
                .param("comision", metodoPago.getComision())
                .param("id", metodoPago.getId())
                .update();

        if (rowsAffected == 0) return null;

        return findById(metodoPago.getId());
    }

    public int deleteById(UUID metodo_pago_id) {
        String sql = "UPDATE metodos_pago SET activo=FALSE WHERE id = :id";

        return jdbc.sql(sql)
                .param("id", metodo_pago_id)
                .update();
    }
}
