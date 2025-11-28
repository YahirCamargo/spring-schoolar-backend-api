package mx.tecnm.backend.api.model;

import java.util.UUID;
public class Domicilio {
    private UUID id;
    private String calle;
    private String numero;
    private String colonia;
    private String cp;
    private String estado;
    private String ciudad;
    private Usuario usuario;
    private boolean preferido;
    private boolean activo;
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo =  activo; }
}
