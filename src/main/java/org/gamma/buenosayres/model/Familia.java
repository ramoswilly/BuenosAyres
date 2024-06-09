package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "familias")
public class Familia {
	@Id
	@GeneratedValue
	@Column(name = "id_familia")
	private UUID id;
	private String apellido;
	@OneToMany(mappedBy = "familia")
	List<Persona> miembros;
	private boolean habilitada;
	public Familia(UUID id, List<Persona> miembros)
	{
		this.id = id;
		this.miembros = miembros;
	}

	public Familia()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public String getApellido()
	{
		return this.apellido;
	}

	public List<Persona> getMiembros()
	{
		return this.miembros;
	}

	public boolean isHabilitada()
	{
		return this.habilitada;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setMiembros(List<Persona> miembros)
	{
		this.miembros = miembros;
	}

	public void setHabilitada(boolean habilitada)
	{
		this.habilitada = habilitada;
	}
}
