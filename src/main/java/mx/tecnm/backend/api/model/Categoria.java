package mx.tecnm.backend.api.model;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

  @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Producto> productos;

  public Categoria() {}
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Column(nullable = false,length = 40)
  private String nombre;

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

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