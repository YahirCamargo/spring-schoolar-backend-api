package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.dto.DomicilioPutDTO;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
public class DomicilioRepository {
    private final JdbcTemplate jdbc;

    public DomicilioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public List<Domicilio> findAll() {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id, preferido FROM domicilios";

        return jdbc.query(sql, (rs, rowNum) -> {
            Domicilio d = new Domicilio();

            d.setId(rs.getObject("id", java.util.UUID.class));
            d.setCalle(rs.getString("calle").trim());
            d.setNumero(rs.getString("numero").trim());
            d.setColonia(rs.getString("colonia").trim());
            d.setCp(rs.getString("cp").trim());
            d.setEstado(rs.getString("estado").trim());
            d.setCiudad(rs.getString("ciudad").trim());
            UUID usuarioId = rs.getObject("usuarios_id", java.util.UUID.class);
            d.setPreferido(rs.getBoolean("preferido"));

            return d;
        });
    }

    public Domicilio findById(UUID domicilio_id) {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id, preferido FROM domicilios WHERE id = ?::uuid";
        
        RowMapper<Domicilio> rowMapper = (rs, rowNum) -> {
            Domicilio d = new Domicilio();
            d.setId(rs.getObject("id", java.util.UUID.class));
            d.setCalle(rs.getString("calle").trim());
            d.setNumero(rs.getString("numero").trim());
            d.setColonia(rs.getString("colonia").trim());
            d.setCp(rs.getString("cp").trim());
            d.setEstado(rs.getString("estado").trim());
            d.setCiudad(rs.getString("ciudad").trim());
            UUID usuarioId = rs.getObject("usuarios_id", java.util.UUID.class);
            d.setPreferido(rs.getBoolean("preferido"));

            return d;
        };

        try {
            return jdbc.queryForObject(sql, new Object[]{ domicilio_id }, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public Domicilio save(DomicilioPostDTO domicilioACrear) {

        String sql = "INSERT INTO domicilios (calle,numero,colonia,cp,estado,ciudad,usuarios_id,preferido) VALUES (?,?,?,?,?,?,?,?) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, domicilioACrear.getCalle());
            ps.setString(2, domicilioACrear.getNumero());
            ps.setString(3, domicilioACrear.getColonia());
            ps.setString(4, domicilioACrear.getCp());
            ps.setString(5, domicilioACrear.getEstado());
            ps.setString(6, domicilioACrear.getCiudad());
            ps.setObject(7, domicilioACrear.getUsuarioId());
            ps.setBoolean(8, domicilioACrear.getPreferido()); 
            return ps;
        }, keyHolder);

        UUID generatedId = keyHolder.getKeyAs(UUID.class);
        //java.sql.Timestamp generatedTimestamp = (java.sql.Timestamp) keyHolder.getKeys().get("fecha_registro");

        Domicilio nuevoDomicilio = findById(generatedId);

        //nuevoDomicilio.setId(generatedId);
        //nuevoDomicilio.setFechaRegistro(generatedTimestamp.toLocalDateTime());
        //nuevoDomicilio.setNombre(domicilioACrear.getNombre());
        
        return nuevoDomicilio;
    }

    public Domicilio update(DomicilioPutDTO domicilio) {
        
        String sql = "UPDATE domicilios SET "
                + "calle = ?, "
                + "numero = ?, "
                + "colonia = ?, "
                + "cp = ?, "
                + "estado = ?, "
                + "ciudad = ?, "
                + "preferido = ? "
                + "WHERE id = ?";

        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, domicilio.getCalle());
            ps.setString(2, domicilio.getNumero());
            ps.setString(3, domicilio.getColonia());
            ps.setString(4, domicilio.getCp());
            ps.setString(5, domicilio.getEstado());
            ps.setString(6, domicilio.getCiudad());
            ps.setBoolean(7, domicilio.isPreferido());
            ps.setObject(8, domicilio.getId()); 
            
            return ps;
        });

        if (rowsAffected == 0) {
            return null;
        }
        
        Domicilio domicilioActualizado = this.findById(domicilio.getId());
 
        
        return domicilioActualizado;
    }

    public int deleteById(UUID domicilio_id) {
        String sql = "DELETE FROM domicilios WHERE id = ?::uuid";
        int rowsAffected = jdbc.update(sql, domicilio_id);
        return rowsAffected;
    }

    public int unsetPreferredByUserId(UUID usuarioId) {
        String sql = "UPDATE domicilios SET preferido = FALSE WHERE usuarios_id = ? AND preferido = TRUE";

        return jdbc.update(sql, usuarioId);
    }
}
