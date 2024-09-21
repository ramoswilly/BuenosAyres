package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "patologias")
public class Patologia {
	@Id
	@GeneratedValue
	private UUID id;
	@Column(name = "nombre_patologia")
	private String nombre;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
}