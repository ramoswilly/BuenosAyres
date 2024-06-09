package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "responsables")
public class Responsable {
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
	private boolean responsableFacturacion;

	public Responsable()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public Persona getPersona()
	{
		return this.persona;
	}

	public String getTelefono()
	{
		return this.telefono;
	}

	public String getEmail()
	{
		return this.email;
	}

	public boolean isResponsableFacturacion()
	{
		return this.responsableFacturacion;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setResponsableFacturacion(boolean responsableFacturacion)
	{
		this.responsableFacturacion = responsableFacturacion;
	}
}
