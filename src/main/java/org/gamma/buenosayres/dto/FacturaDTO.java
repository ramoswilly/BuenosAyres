package org.gamma.buenosayres.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FacturaDTO {
    private UUID id;
    private Date periodo;
    private UUID id_facturado;
    private String dni_facturado;
    private String nombre_facturado;
    private String apellido_facturado;
    private float montoFinal;
    private List<DetalleFacturaDTO> detalles;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Date getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}
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
	public float getMontoFinal() {
		return montoFinal;
	}
	public void setMontoFinal(float montoFinal) {
		this.montoFinal = montoFinal;
	}
	public List<DetalleFacturaDTO> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleFacturaDTO> detalles) {
		this.detalles = detalles;
	}

}
