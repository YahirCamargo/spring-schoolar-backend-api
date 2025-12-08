package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.dto.DomicilioPutDTO;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class DomicilioRepository {
    private final JdbcClient jdbc;

    public DomicilioRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Domicilio> findAll() {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id, preferido FROM domicilios WHERE activo=TRUE";

        try{
            return jdbc.sql(sql)
                .query((rs, rowNum) -> {
            Domicilio d = new Domicilio();

            d.setId(rs.getObject("id", java.util.UUID.class));
            d.setCalle(rs.getString("calle"));
            d.setNumero(rs.getString("numero"));
            d.setColonia(rs.getString("colonia"));
            d.setCp(rs.getString("cp"));
            d.setEstado(rs.getString("estado"));
            d.setCiudad(rs.getString("ciudad"));
            d.setUsuarioId(rs.getObject("usuarios_id", UUID.class));
            d.setPreferido(rs.getBoolean("preferido"));

            return d;
        })
        .list();
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Optional<Domicilio> findById(UUID domicilioId) {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id, preferido FROM domicilios WHERE id = :id AND activo = TRUE";
        
        try {
            Domicilio domicilio = jdbc.sql(sql)
                .param("id",domicilioId)
                .query((rs,rowNum) ->{
                    Domicilio d = new Domicilio();
                    d.setId(rs.getObject("id", java.util.UUID.class));
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setColonia(rs.getString("colonia"));
                    d.setCp(rs.getString("cp"));
                    d.setEstado(rs.getString("estado"));
                    d.setCiudad(rs.getString("ciudad"));
                    d.setUsuarioId(rs.getObject("usuarios_id", UUID.class));
                    d.setPreferido(rs.getBoolean("preferido"));

                    return d;
                }).single();

            return Optional.of(domicilio);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Domicilio> findPreferidoByUsuarioId(UUID usuarioId) {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id, preferido FROM domicilios WHERE usuarios_id = :usuario_id AND preferido=TRUE AND activo = TRUE";
        
        try {
            Domicilio domicilio = jdbc.sql(sql)
                .param("usuario_id",usuarioId)
                .query((rs,rowNum) ->{
                    Domicilio d = new Domicilio();
                    d.setId(rs.getObject("id", java.util.UUID.class));
                    d.setCalle(rs.getString("calle"));
                    d.setNumero(rs.getString("numero"));
                    d.setColonia(rs.getString("colonia"));
                    d.setCp(rs.getString("cp"));
                    d.setEstado(rs.getString("estado"));
                    d.setCiudad(rs.getString("ciudad"));
                    d.setUsuarioId(rs.getObject("usuarios_id", UUID.class));
                    d.setPreferido(rs.getBoolean("preferido"));

                    return d;
                }).single();
            return Optional.of(domicilio);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public Domicilio save(DomicilioPostDTO domicilioACrear) {

        String sql = """
        INSERT INTO domicilios (calle,numero,colonia,cp,estado,ciudad,usuarios_id,preferido) 
        VALUES (:calle,:numero,:colonia,:cp,:estado,:ciudad,:usuarios_id,:preferido) RETURNING id
        """;

        UUID generatedId = jdbc.sql(sql)
            .param("calle", domicilioACrear.getCalle())
            .param("numero", domicilioACrear.getNumero())
            .param("colonia", domicilioACrear.getColonia())
            .param("cp", domicilioACrear.getCp())
            .param("estado", domicilioACrear.getEstado())
            .param("ciudad", domicilioACrear.getCiudad())
            .param("usuarios_id", domicilioACrear.getUsuarioId())
            .param("preferido", domicilioACrear.isPreferido())
            .query((rs,rowNum)-> rs.getObject("id",UUID.class))
            .single();


        Domicilio nuevoDomicilio = new Domicilio();

        nuevoDomicilio.setId(generatedId);
        nuevoDomicilio.setCalle(domicilioACrear.getCalle());
        nuevoDomicilio.setNumero(domicilioACrear.getNumero());
        nuevoDomicilio.setColonia(domicilioACrear.getColonia());
        nuevoDomicilio.setCp(domicilioACrear.getCp());
        nuevoDomicilio.setEstado(domicilioACrear.getEstado());
        nuevoDomicilio.setCiudad(domicilioACrear.getCiudad());
        nuevoDomicilio.setUsuarioId(domicilioACrear.getUsuarioId());
        nuevoDomicilio.setPreferido(domicilioACrear.isPreferido());
        
        return nuevoDomicilio;
    }

    public Optional<Domicilio> update(DomicilioPutDTO domicilioAActualizar, UUID domicilioId) {
        
        String sql = "UPDATE domicilios SET "
                + "calle = :calle, "
                + "numero = :numero, "
                + "colonia = :colonia, "
                + "cp = :cp, "
                + "estado = :estado, "
                + "ciudad = :ciudad, "
                + "preferido = :preferido "
                + "WHERE id = :id AND activo=TRUE";

        int rowsAffected = jdbc.sql(sql)
            .param("calle", domicilioAActualizar.getCalle())
            .param("numero", domicilioAActualizar.getNumero())
            .param("colonia", domicilioAActualizar.getColonia())
            .param("cp", domicilioAActualizar.getCp())
            .param("estado", domicilioAActualizar.getEstado())
            .param("ciudad", domicilioAActualizar.getCiudad())
            .param("preferido", domicilioAActualizar.isPreferido())
            .param("id",domicilioId)
            .update();
            
        if (rowsAffected == 0) {
            return Optional.empty();
        }

        return findById(domicilioId);
    }

    public int deactivateById(UUID domicilioId) {
        String sql = "UPDATE domicilios SET activo=FALSE WHERE id = :id AND activo = TRUE";
        int rowsAffected = jdbc.sql(sql)
            .param("id",domicilioId)
            .update();
        return rowsAffected;
    }

    public int unsetPreferredByUserId(UUID domicilioId) {
        String sql = "UPDATE domicilios SET preferido = FALSE WHERE usuarios_id = :id AND preferido = TRUE";

        return jdbc.sql(sql)
            .param("id",domicilioId).update();
    }
}
