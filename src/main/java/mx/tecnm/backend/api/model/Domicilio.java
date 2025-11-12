package mx.tecnm.backend.api.model;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "domicilios",
    indexes = {
        @Index(name = "idx_estado_ciudad", columnList = "estado, ciudad")
    }
)
public class Domicilio {
    public Domicilio() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=45)
    private String calle;

    @Column(nullable=false,length=10)
    private String numero;

    @Column(nullable=false,length=30)
    private String colonia;

    @Column(nullable=false,length=5)
    private String cp;

    @Column(nullable=false,length=20)
    private String estado;

    @Column(nullable=false,length=45)
    private String ciudad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false, foreignKey = @ForeignKey(name = "fk_domicilios_usuarios"))
    @JsonBackReference
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean preferido = false;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public boolean isPreferido() { return preferido; }
    public void setPreferido(boolean preferido) { this.preferido=preferido; }
}

/* 
 CREATE TABLE `domicilios` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `calle` varchar(45) NOT NULL,
  `numero` varchar(10) NOT NULL COMMENT 'Ej: 123A, S/N, 456-B',
  `colonia` varchar(30) NOT NULL,
  `cp` char(5) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `ciudad` varchar(45) NOT NULL,
  `usuarios_id` smallint(5) unsigned NOT NULL,
  `preferido` BOOLEAN NOT NULL DEFAULT false
  PRIMARY KEY (`id`),
  KEY `fk_domicilios_usuarios_idx` (`usuarios_id`),
  KEY `idx_estado_ciudad` (`estado`,`ciudad`),
  CONSTRAINT `fk_domicilios_usuarios` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB
*/