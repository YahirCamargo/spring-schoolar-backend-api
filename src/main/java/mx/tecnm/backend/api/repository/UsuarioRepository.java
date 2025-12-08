package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Usuario;
import mx.tecnm.backend.api.dto.UsuarioPostDTO;
import mx.tecnm.backend.api.dto.UsuarioPutDTO;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

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

        String sql = "SELECT id,nombre,email,telefono,sexo,fecha_nacimiento,fecha_registro FROM usuarios WHERE activo=TRUE";

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

    public Optional<Usuario> findById(UUID usuarioId) {
        String sql = "SELECT id,nombre,email,telefono,sexo,fecha_nacimiento,rol FROM usuarios WHERE id = :id AND activo=TRUE";
        
        try {
            Usuario usuario = jdbc.sql(sql)
                .param("id",usuarioId)
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
            return Optional.of(usuario);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty(); 
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


    public Optional<Usuario> update(UsuarioPutDTO usuarioAActualizar, UUID usuarioId) {
        String sql = """
            UPDATE usuarios SET
                nombre = :nombre,
                email = :email,
                telefono = :telefono,
                sexo = :sexo,
                fecha_nacimiento = :fecha_nacimiento,
                contrasena = :contrasena
            WHERE id = :id AND activo=TRUE
            """;

        int rows = jdbc.sql(sql)
                .param("nombre", usuarioAActualizar.getNombre())
                .param("email", usuarioAActualizar.getEmail())
                .param("telefono", usuarioAActualizar.getTelefono())
                .param("sexo", usuarioAActualizar.getSexo())
                .param("fecha_nacimiento", java.sql.Date.valueOf(usuarioAActualizar.getFechaNacimiento()))
                .param("contrasena", usuarioAActualizar.getContrasena())
                .param("id", usuarioId)
                .update();

        if (rows == 0) {
            return Optional.empty();
        }

        return this.findById(usuarioId);
    }


    public int deactivateById(UUID usuarioId) {
        String sql = "UPDATE usuarios SET activo=FALSE WHERE id = :id AND activo=TRUE";
        return jdbc.sql(sql)
            .param("id", usuarioId)
            .update();
    }

}