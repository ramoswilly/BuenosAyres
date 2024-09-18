package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "inasistencias")
public class Inasistencia {
	@Id
	@GeneratedValue
	@Column(name = "id_inasistencia")
	private UUID id;
	@OneToOne
	@JoinColumn(name = "id_alumno")
	private Alumno alumno;
	@Column(name = "fecha")
	private LocalDate fecha;
	@Column(name = "cantidad")
	private int cantidad;
	@Column(name = "justificada")
	private boolean justificada;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public Alumno getAlumno()
	{
		return alumno;
	}

	public void setAlumno(Alumno alumno)
	{
		this.alumno = alumno;
	}

	public LocalDate getFecha()
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha)
	{
		this.fecha = fecha;
	}

	public int getCantidad()
	{
		return cantidad;
	}

	public void setCantidad(int cantidad)
	{
		this.cantidad = cantidad;
	}

	public boolean isJustificada()
	{
		return justificada;
	}

	public void setJustificada(boolean justificada)
	{
		this.justificada = justificada;
	}
}
