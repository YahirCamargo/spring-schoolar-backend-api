package mx.tecnm.backend.api.repository;

import mx.tecnm.backend.api.model.Domicilio;
import mx.tecnm.backend.api.dto.DomicilioPostDTO;
import mx.tecnm.backend.api.dto.DomicilioPutDTO;
import java.util.UUID;
import java.util.List;

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

    public Domicilio findById(UUID domicilio_id) {
        String sql = "SELECT id, calle, numero, colonia, cp, estado, ciudad, usuarios_id, preferido FROM domicilios WHERE id = :id AND activo = TRUE";
        
        try {
            return jdbc.sql(sql)
                .param("id",domicilio_id)
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
            
        } catch (EmptyResultDataAccessException e) {
            return null; 
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
            .param("preferido", domicilioACrear.getPreferido())
            .query((rs,rowNum)-> rs.getObject("id",UUID.class))
            .single();


        Domicilio nuevoDomicilio = findById(generatedId);

        nuevoDomicilio.setId(generatedId);
        nuevoDomicilio.setCalle(domicilioACrear.getCalle());
        nuevoDomicilio.setNumero(domicilioACrear.getNumero());
        nuevoDomicilio.setColonia(domicilioACrear.getColonia());
        nuevoDomicilio.setCp(domicilioACrear.getCp());
        nuevoDomicilio.setEstado(domicilioACrear.getEstado());
        nuevoDomicilio.setCiudad(domicilioACrear.getCiudad());
        nuevoDomicilio.setPreferido(domicilioACrear.getPreferido());
        
        return nuevoDomicilio;
    }

    public Domicilio update(DomicilioPutDTO domicilio, UUID domicilioId) {
        
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
            .param("calle", domicilio.getCalle())
            .param("numero", domicilio.getNumero())
            .param("colonia", domicilio.getColonia())
            .param("cp", domicilio.getCp())
            .param("estado", domicilio.getEstado())
            .param("ciudad", domicilio.getCiudad())
            .param("preferido", domicilio.isPreferido())
            .param("id",domicilioId)
            .update();
            
        if (rowsAffected == 0) {
            return null;
        }

        Domicilio domicilioActualizado = this.findById(domicilioId);
        return domicilioActualizado;
    }

    public int deactivateById(UUID domicilio_id) {
        String sql = "UPDATE domicilios SET activo=FALSE WHERE id = :id AND activo = TRUE";
        int rowsAffected = jdbc.sql(sql)
            .param("id",domicilio_id)
            .update();
        return rowsAffected;
    }

    public int unsetPreferredByUserId(UUID domicilio_id) {
        String sql = "UPDATE domicilios SET preferido = FALSE WHERE usuarios_id = :id AND preferido = TRUE";

        return jdbc.sql(sql)
            .param("id",domicilio_id).update();
    }
}
