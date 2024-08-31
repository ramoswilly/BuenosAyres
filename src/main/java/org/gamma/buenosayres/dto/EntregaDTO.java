package org.gamma.buenosayres.dto;

import java.util.Date;
import java.util.UUID;

public class EntregaDTO {
	private UUID id;
	private UUID idEvaluacion;
	private UUID idAlumno;
	private String comentarios;
	private Date fecha;
	private String nombreAlumno;
	private String apellidoAlumno;
	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public UUID getIdEvaluacion()
	{
		return idEvaluacion;
	}

	public void setIdEvaluacion(UUID idEvaluacion)
	{
		this.idEvaluacion = idEvaluacion;
	}

	public UUID getIdAlumno()
	{
		return idAlumno;
	}

	public void setIdAlumno(UUID idAlumno)
	{
		this.idAlumno = idAlumno;
	}

	public String getComentarios()
	{
		return comentarios;
	}

	public void setComentarios(String comentarios)
	{
		this.comentarios = comentarios;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public String getNombreAlumno()
	{
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno)
	{
		this.nombreAlumno = nombreAlumno;
	}

	public String getApellidoAlumno()
	{
		return apellidoAlumno;
	}

	public void setApellidoAlumno(String apellidoAlumno)
	{
		this.apellidoAlumno = apellidoAlumno;
	}
}