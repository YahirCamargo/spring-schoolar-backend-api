package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.MetodoPago;
import mx.tecnm.backend.api.dto.MetodoPagoPostDTO;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class MetodoPagoRepository {
    private final JdbcTemplate jdbc;

    public MetodoPagoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public List<MetodoPago> findAll() {
        String sql = "SELECT id,nombre,comision FROM metodos_pago";

        return jdbc.query(sql, (rs, rowNum) -> {
            MetodoPago mP = new MetodoPago();

            mP.setId(UUID.fromString(rs.getString("id")));
            mP.setNombre(rs.getString("nombre"));
            mP.setComision(rs.getBigDecimal("comision"));
            return mP;
        });
    }

    public MetodoPago findById(UUID metodo_pago_id) {
        String sql = "SELECT id,nombre,comision FROM metodos_pago WHERE id = ?::uuid";
        
        RowMapper<MetodoPago> rowMapper = (rs, rowNum) -> {
            MetodoPago mP = new MetodoPago();
            mP.setId(rs.getObject("id", java.util.UUID.class));
            mP.setNombre(rs.getString("nombre").trim());
            mP.setComision(rs.getBigDecimal("comision"));
            return mP;
        };

        try {
            return jdbc.queryForObject(sql, new Object[]{ metodo_pago_id }, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public MetodoPago save(MetodoPagoPostDTO metodoPagoACrear) {
        String sql = "INSERT INTO metodos_pago (nombre,comision) VALUES (?,?) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, metodoPagoACrear.getNombre());
            ps.setBigDecimal(2, metodoPagoACrear.getComision());
            return ps;
        }, keyHolder);

        UUID generatedId = keyHolder.getKeyAs(UUID.class);
        //java.sql.Timestamp generatedTimestamp = (java.sql.Timestamp) keyHolder.getKeys().get("fecha_registro");

        MetodoPago nuevaMetodoPago = findById(generatedId);

        //nuevaMetodoPago.setId(generatedId);
        //nuevaMetodoPago.setFechaRegistro(generatedTimestamp.toLocalDateTime());
        //nuevaMetodoPago.setNombre(metodoPagoACrear.getNombre());
        
        return nuevaMetodoPago;
    }

    public MetodoPago update(MetodoPago metodoPago) {
        
        String sql = "UPDATE metodos_pago SET "
                + "nombre = ?, "
                + "comision = ? "
                + "WHERE id = ?";

        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, metodoPago.getNombre());
            ps.setBigDecimal(2, metodoPago.getComision());
            ps.setObject(3, metodoPago.getId()); 
            return ps;
        });
        if (rowsAffected == 0) {
            return null;
        }
        return this.findById(metodoPago.getId());
    }

    public int deleteById(UUID metodo_pago_id) {
        String sql = "DELETE FROM metodos_pago WHERE id = ?";
        int rowsAffected = jdbc.update(sql, metodo_pago_id);
        return rowsAffected;
    }
}
