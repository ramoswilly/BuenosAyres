package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "padres")
public class Padre {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	private String telefono;
	private String email;
	private String cuit;
	private boolean habilitado;
	@Column(name = "es_responsable_facturacion")
	private boolean responsableFacturacion;

	public Padre()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public Persona getPersona()
	{
		return this.persona;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}

	public String getTelefono()
	{
		return this.telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isResponsableFacturacion()
	{
		return this.responsableFacturacion;
	}

	public void setResponsableFacturacion(boolean responsableFacturacion)
	{
		this.responsableFacturacion = responsableFacturacion;
	}

	public boolean isHabilitado()
	{
		return habilitado;
	}

	public void setHabilitado(boolean habilitado)
	{
		this.habilitado = habilitado;
	}

	public String getCuit()
	{
		return cuit;
	}

	public void setCuit(String cuit)
	{
		this.cuit = cuit;
	}
}
