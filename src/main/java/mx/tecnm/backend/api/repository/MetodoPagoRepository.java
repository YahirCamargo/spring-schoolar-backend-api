package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

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
        String sql = "SELECT id,nombre,comision FROM metodos_pago WHERE activo=TRUE";

        return jdbc.sql(sql)
                        .query((rs, rowNum) -> {
                            MetodoPago mP = new MetodoPago();
                            mP.setId(rs.getObject("id", UUID.class));
                            mP.setNombre(rs.getString("nombre"));
                            mP.setComision(rs.getBigDecimal("comision"));
                            return mP;
                        })
                        .list();

    }

    public Optional<MetodoPago> findById(UUID metodoPagoId) {
        String sql = "SELECT id,nombre,comision FROM metodos_pago WHERE id = :id AND activo = TRUE";
    
        try {
            MetodoPago metodoPago = jdbc.sql(sql).param("id",metodoPagoId)
                        .query((rs, rowNum) -> {
                            MetodoPago mP = new MetodoPago();
                            mP.setId(rs.getObject("id", UUID.class));
                            mP.setNombre(rs.getString("nombre"));
                            mP.setComision(rs.getBigDecimal("comision"));
                            return mP;
                        })
                        .single();
            return Optional.of(metodoPago);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); 
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

    public Optional<MetodoPago> update(MetodoPagoPostDTO metodoPagoAActualizar, UUID metodoPagoId) {
        
        String sql = "UPDATE metodos_pago SET "
                + "nombre = :nombre, "
                + "comision = :comision "
                + "WHERE id = :id AND activo=TRUE";

        int rowsAffected = jdbc.sql(sql)
                .param("nombre", metodoPagoAActualizar.getNombre())
                .param("comision", metodoPagoAActualizar.getComision())
                .param("id", metodoPagoId)
                .update();

        if (rowsAffected == 0) return Optional.empty();

        return findById(metodoPagoId);
    }

    public int deleteById(UUID metodoPagoId) {
        String sql = "UPDATE metodos_pago SET activo=FALSE WHERE id = :id AND activo=TRUE";

        return jdbc.sql(sql)
                .param("id", metodoPagoId)
                .update();
    }
}
