package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class Usuario {
	@Id
	private UUID id;
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	private String username;
	private String password;
	private boolean enabled;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Rol> roles = new HashSet<>();

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public Set<Rol> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Rol> roles)
	{
		this.roles = roles;
	}

	public void addRol(Rol rol)
	{
		roles.add(rol);
	}

	public Persona getPersona()
	{
		return persona;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}
}