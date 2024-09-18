package org.gamma.buenosayres.dto;

import java.time.LocalDate;
import java.util.UUID;

public class InasistenciaDTO {
	private UUID id;
	private UUID alumnoId;
	private String nombre;
	private String apellido;
	private String dni;
	private LocalDate fecha;
	private int cantidad;
	private boolean justificada;

	// Getters and Setters

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(UUID alumnoId) {
		this.alumnoId = alumnoId;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isJustificada() {
		return justificada;
	}

	public void setJustificada(boolean justificada) {
		this.justificada = justificada;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getDni()
	{
		return dni;
	}

	public void setDni(String dni)
	{
		this.dni = dni;
	}
}