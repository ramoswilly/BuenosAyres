package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "salud")
public class Salud {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	@Column(name = "obra_social")
	private String obraSocial;
	@Column(name = "numero_afiliado")
	private String numeroAfiliado;
	@ManyToMany
	@JoinTable(
			name = "patologias-personas",
			joinColumns = @JoinColumn(name = "id_persona"),
			inverseJoinColumns = @JoinColumn(name = "id_patologia"))
	private List<Patologia> patologias;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public Persona getPersona()
	{
		return persona;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}

	public String getObraSocial()
	{
		return obraSocial;
	}

	public void setObraSocial(String obraSocial)
	{
		this.obraSocial = obraSocial;
	}

	public String getNumeroAfiliado()
	{
		return numeroAfiliado;
	}

	public void setNumeroAfiliado(String numeroAfiliado)
	{
		this.numeroAfiliado = numeroAfiliado;
	}

	public List<Patologia> getPatologias()
	{
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias)
	{
		this.patologias = patologias;
	}
}
