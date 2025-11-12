package mx.tecnm.backend.api.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;



@Entity
@Table(name = "usuarios")
public class Usuario {
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domicilio> domicilios;
    
    public Usuario() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String nombre;
    
    @Column(nullable = false, length = 60, unique = true)
    private String email;
    
    @Column(length = 10)
    private String telefono; 
    
    @Column(nullable = false, length = 1) 
    private String sexo;

    @Column(name = "fecha_nacimiento", nullable = false) 
    private LocalDateTime fechaNacimiento;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false) 
    private LocalDateTime fechaRegistro;
    
    @Column(name = "rol", nullable = false, length = 20) 
    private String rol;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public LocalDateTime getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) {this.contrasena = contrasena; } 
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}