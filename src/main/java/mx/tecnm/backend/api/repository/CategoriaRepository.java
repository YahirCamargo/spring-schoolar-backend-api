package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
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
public class CategoriaRepository {
    private final JdbcTemplate jdbc;

    public CategoriaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public List<Categoria> findAll() {
        String sql = "SELECT id,nombre FROM categorias";

        return jdbc.query(sql, (rs, rowNum) -> {
            Categoria c = new Categoria();

            c.setId(UUID.fromString(rs.getString("id")));
            c.setNombre(rs.getString("nombre"));
            return c;
        });
    }

    public Categoria findById(UUID categoria_id) {
        String sql = "SELECT id,nombre FROM categorias WHERE id = ?::uuid";
        
        RowMapper<Categoria> rowMapper = (rs, rowNum) -> {
            Categoria c = new Categoria();
            c.setId(rs.getObject("id", java.util.UUID.class));
            c.setNombre(rs.getString("nombre").trim());
            
            return c;
        };

        try {
            return jdbc.queryForObject(sql, new Object[]{ categoria_id }, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public Categoria save(CategoriaPostDTO categoriaACrear) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoriaACrear.getNombre());
            
            return ps;
        }, keyHolder);

        UUID generatedId = keyHolder.getKeyAs(UUID.class);
        //java.sql.Timestamp generatedTimestamp = (java.sql.Timestamp) keyHolder.getKeys().get("fecha_registro");

        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setId(generatedId);
        //nuevaCategoria.setFechaRegistro(generatedTimestamp.toLocalDateTime());
        nuevaCategoria.setNombre(categoriaACrear.getNombre());
        
        return nuevaCategoria;
    }

    public Categoria update(Categoria categoria) {
        
        String sql = "UPDATE categorias SET "
                + "nombre = ? "
                + "WHERE id = ?";

        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, categoria.getNombre());
            ps.setObject(2, categoria.getId()); 
            
            return ps;
        });
        if (rowsAffected == 0) {
            return null;
        }
        return this.findById(categoria.getId());
    }

    public int deleteById(UUID categoria_id_id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        int rowsAffected = jdbc.update(sql, categoria_id_id);
        return rowsAffected;
    }

}
