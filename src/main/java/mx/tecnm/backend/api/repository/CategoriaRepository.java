package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Categoria;
import mx.tecnm.backend.api.dto.CategoriaPostDTO;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class CategoriaRepository {

    private final JdbcClient jdbc;

    public CategoriaRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Categoria> findAll() {
        String sql = "SELECT id, nombre FROM categorias WHERE activo=TRUE";

        return jdbc.sql(sql)
                .query((rs, rowNum) -> {
                    Categoria c = new Categoria();
                    c.setId(rs.getObject("id", UUID.class));
                    c.setNombre(rs.getString("nombre"));
                    return c;
                })
                .list();
    }

    public Categoria findById(UUID categoriaId) {
        String sql = "SELECT id, nombre FROM categorias WHERE id = :id AND activo=TRUE";

        try {
            return jdbc.sql(sql)
                    .param("id", categoriaId)
                    .query((rs, rowNum) -> {
                        Categoria c = new Categoria();
                        c.setId(rs.getObject("id", UUID.class));
                        c.setNombre(rs.getString("nombre"));
                        return c;
                    })
                    .single();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Categoria save(CategoriaPostDTO categoriaACrear) {
        String sql = "INSERT INTO categorias (nombre) VALUES (:nombre) RETURNING id";

        UUID generatedId = jdbc.sql(sql)
                .param("nombre", categoriaACrear.getNombre())
                .query((rs, rowNum) -> rs.getObject("id", UUID.class))
                .single();

        Categoria nueva = new Categoria();
        nueva.setId(generatedId);
        nueva.setNombre(categoriaACrear.getNombre());

        return nueva;
    }


    public Categoria update(CategoriaPostDTO categoria, UUID categoriaId) {
        String sql = """
                UPDATE categorias SET
                    nombre = :nombre
                WHERE id = :id AND activo=TRUE
                """;

        int rowsAffected = jdbc.sql(sql)
                .param("nombre", categoria.getNombre())
                .param("id", categoriaId)
                .update();

        if (rowsAffected == 0) return null;

        return findById(categoriaId);
    }

    public int deactivateById(UUID id) {
        String sql = "UPDATE categorias SET activo=FALSE WHERE id = :id AND activo=TRUE";

        return jdbc.sql(sql)
                .param("id", id)
                .update();
    }
}
