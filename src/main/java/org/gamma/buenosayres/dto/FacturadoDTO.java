package org.gamma.buenosayres.dto;
import java.util.UUID;

class FacturadoDTO {
    private UUID id_facturado;
    private String dni_facturado;
    private String nombre_facturado;
    private String apellido_facturado;

    public FacturadoDTO(){}

    public UUID getId_facturado() {
        return id_facturado;
    }
    public void setId_facturado(UUID id_facturado) {
        this.id_facturado = id_facturado;
    }
    public String getDni_facturado() {
        return dni_facturado;
    }
    public void setDni_facturado(String dni_facturado) {
        this.dni_facturado = dni_facturado;
    }
    public String getNombre_facturado() {
        return nombre_facturado;
    }
    public void setNombre_facturado(String nombre_facturado) {
        this.nombre_facturado = nombre_facturado;
    }
    public String getApellido_facturado() {
        return apellido_facturado;
    }
    public void setApellido_facturado(String apellido_facturado) {
        this.apellido_facturado = apellido_facturado;
    }

}
