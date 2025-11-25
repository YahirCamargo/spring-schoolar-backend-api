package mx.tecnm.backend.api.dto;

import java.time.LocalDate;
import java.util.UUID;

public class UsuarioDTO {

    private final UUID id;
    private final String nombre;
    private final String email;
    private final String telefono;
    private final String sexo;
    private final LocalDate fechaNacimiento;
    

    public UsuarioDTO(UUID id,String nombre,String email,String telefono,String sexo,LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getSexo() { return sexo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}