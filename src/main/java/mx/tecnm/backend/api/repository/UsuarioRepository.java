package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.dto.UsuarioDTO;
import mx.tecnm.backend.api.dto.UsuarioPostDTO;
import mx.tecnm.backend.api.dto.UsuarioPutDTO;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class UsuarioRepository {
    private final JdbcTemplate jdbc;

    public UsuarioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Usuario> findAll() {

        String sql = "SELECT id,nombre,email,telefono,sexo,fecha_nacimiento,fecha_registro FROM usuarios";

        return jdbc.query(sql, (rs, rowNum) -> {
            Usuario u = new Usuario();

            u.setId(UUID.fromString(rs.getString("id")));
            u.setNombre(rs.getString("nombre"));
            u.setEmail(rs.getString("email"));
            u.setTelefono(rs.getString("telefono"));
            u.setSexo(rs.getString("sexo"));
            u.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            return u;
        });
    }

    public Usuario findById(UUID usuario_id) {
        String sql = "SELECT id,nombre,email,telefono,sexo,fecha_nacimiento,rol FROM usuarios WHERE id = ?::uuid";
        
        RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
            Usuario u = new Usuario();
            u.setId(rs.getObject("id", java.util.UUID.class));
            u.setNombre(rs.getString("nombre").trim());
            u.setEmail(rs.getString("email").trim());
            u.setTelefono(rs.getString("telefono").trim());
            u.setSexo(rs.getString("sexo").trim());
            u.setRol(rs.getString("rol").trim());
            u.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            
            return u;
        };

        try {
            return jdbc.queryForObject(sql, new Object[]{ usuario_id }, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public Usuario save(UsuarioPostDTO usuarioACrear) {
        String sql = "INSERT INTO usuarios (nombre, email, telefono, sexo, fecha_nacimiento, contrasena) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuarioACrear.getNombre());
            ps.setString(2, usuarioACrear.getEmail());
            ps.setString(3, usuarioACrear.getTelefono());
            ps.setString(4, usuarioACrear.getSexo());
            ps.setDate(5, java.sql.Date.valueOf(usuarioACrear.getFechaNacimiento()));
            ps.setString(6, usuarioACrear.getContrasena());
            
            return ps;
        }, keyHolder);

        UUID generatedId = keyHolder.getKeyAs(UUID.class);
        //java.sql.Timestamp generatedTimestamp = (java.sql.Timestamp) keyHolder.getKeys().get("fecha_registro");

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setId(generatedId);
        //nuevoUsuario.setFechaRegistro(generatedTimestamp.toLocalDateTime());
        nuevoUsuario.setNombre(usuarioACrear.getNombre());
        
        return nuevoUsuario;
    }

    public Usuario update(UsuarioPutDTO usuario) {
        
        String sql = "UPDATE usuarios SET "
                + "nombre = ?, "
                + "email = ?, "
                + "telefono = ?, "
                + "sexo = ?, "
                + "fecha_nacimiento = ?, "
                + "contrasena = ? "
                + "WHERE id = ?";

        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getSexo());
            ps.setDate(5, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(6, usuario.getContrasena());
            ps.setObject(7, usuario.getId()); 
            
            return ps;
        });
        if (rowsAffected == 0) {
            return null;
        }
        return this.findById(usuario.getId());
    }

    public int deleteById(UUID user_id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        int rowsAffected = jdbc.update(sql, user_id);
        return rowsAffected;
    }

}