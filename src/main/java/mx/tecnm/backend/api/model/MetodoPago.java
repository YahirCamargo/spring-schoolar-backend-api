package mx.tecnm.backend.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "metodos_pago",
    indexes = {
        @Index(name = "idx_nombre", columnList = "nombre")
    }
)
public class MetodoPago {
    public MetodoPago() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 25)
    private String nombre;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal comision;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getComision() { return comision; }
    public void setComision(BigDecimal comision) { this.comision = comision; }
}

/*
 CREATE TABLE `metodos_pago` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `comision` decimal(4,2) NOT NULL DEFAULT '1.50',
  PRIMARY KEY (`id`),
  KEY `idx_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
*/