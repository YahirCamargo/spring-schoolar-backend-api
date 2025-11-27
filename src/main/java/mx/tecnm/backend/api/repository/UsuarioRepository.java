package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.dto.UsuarioPostDTO;
import mx.tecnm.backend.api.dto.UsuarioPutDTO;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class UsuarioRepository {
    private final JdbcClient jdbc;

    public UsuarioRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Usuario> findAll() {

        String sql = "SELECT id,nombre,email,telefono,sexo,fecha_nacimiento,fecha_registro FROM usuarios";

            return jdbc.sql(sql)
                .query((rs, rowNum) -> {
                Usuario u = new Usuario();

                u.setId(rs.getObject("id",UUID.class));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setTelefono(rs.getString("telefono"));
                u.setSexo(rs.getString("sexo"));
                u.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                return u;
            }).list();
    }

    public Usuario findById(UUID usuario_id) {
        String sql = "SELECT id,nombre,email,telefono,sexo,fecha_nacimiento,rol FROM usuarios WHERE id = ?::uuid";
        
        try {
            return jdbc.sql(sql)
                .param("id",usuario_id)
                .query((rs, rowNum) -> {
                    Usuario u = new Usuario();
                    u.setId(rs.getObject("id",UUID.class));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setSexo(rs.getString("sexo"));
                    u.setRol(rs.getString("rol"));
                    u.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    return u;
                }).single();
        }catch (EmptyResultDataAccessException e) {
            return null; 
        }
    }

    public Usuario save(UsuarioPostDTO usuarioACrear) {
        String sql = """
            INSERT INTO usuarios (nombre, email, telefono, sexo, fecha_nacimiento, contrasena)
            VALUES (:nombre, :email, :telefono, :sexo, :fechaNacimiento, :contrasena)
            RETURNING id
            """;

        UUID generatedId = jdbc.sql(sql)
                .param("nombre", usuarioACrear.getNombre())
                .param("email", usuarioACrear.getEmail())
                .param("telefono", usuarioACrear.getTelefono())
                .param("sexo", usuarioACrear.getSexo())
                .param("fechaNacimiento", java.sql.Date.valueOf(usuarioACrear.getFechaNacimiento()))
                .param("contrasena", usuarioACrear.getContrasena())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        Usuario nuevo = new Usuario();
        nuevo.setId(generatedId);
        nuevo.setNombre(usuarioACrear.getNombre());
        nuevo.setEmail(usuarioACrear.getEmail());
        nuevo.setTelefono(usuarioACrear.getTelefono());
        nuevo.setSexo(usuarioACrear.getSexo());
        nuevo.setFechaNacimiento(usuarioACrear.getFechaNacimiento());

        return nuevo;
    }


    public Usuario update(UsuarioPutDTO usuario) {
        String sql = """
            UPDATE usuarios SET
                nombre = :nombre,
                email = :email,
                telefono = :telefono,
                sexo = :sexo,
                fecha_nacimiento = :fecha_nacimiento,
                contrasena = :contrasena
            WHERE id = :id
            """;

        int rows = jdbc.sql(sql)
                .param("nombre", usuario.getNombre())
                .param("email", usuario.getEmail())
                .param("telefono", usuario.getTelefono())
                .param("sexo", usuario.getSexo())
                .param("fecha_nacimiento", java.sql.Date.valueOf(usuario.getFechaNacimiento()))
                .param("contrasena", usuario.getContrasena())
                .param("id", usuario.getId())
                .update();

        if (rows == 0) {
            return null;
        }

        return this.findById(usuario.getId());
    }


    public int deleteById(UUID user_id) {
        String sql = "DELETE FROM usuarios WHERE id = :id";
        return jdbc.sql(sql)
            .param("id",user_id)
            .update();
    }

}