package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sanciones")
public class Sancion {
	@Id
	@GeneratedValue
	private UUID id;
	private LocalDate fecha;
	@ManyToOne
	private Alumno alumno;
	private String causa;
	private Gravedad gravedad;
	private boolean habilitada;
	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public LocalDate getFecha()
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha)
	{
		this.fecha = fecha;
	}

	public Alumno getAlumno()
	{
		return alumno;
	}

	public void setAlumno(Alumno alumno)
	{
		this.alumno = alumno;
	}

	public String getCausa()
	{
		return causa;
	}

	public void setCausa(String causa)
	{
		this.causa = causa;
	}

	public Gravedad getGravedad()
	{
		return gravedad;
	}

	public void setGravedad(Gravedad gravedad)
	{
		this.gravedad = gravedad;
	}

	public boolean isHabilitada()
	{
		return habilitada;
	}

	public void setHabilitada(boolean habilitada)
	{
		this.habilitada = habilitada;
	}
}
