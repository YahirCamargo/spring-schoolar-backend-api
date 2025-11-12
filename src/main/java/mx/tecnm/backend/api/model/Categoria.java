package mx.tecnm.backend.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    public Categoria() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 40)
    private String nombre;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}


/* 
 * CREATE TABLE `categorias` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `idx_nombre_fulltext` (`nombre`)
)
*/