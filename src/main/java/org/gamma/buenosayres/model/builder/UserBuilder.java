package org.gamma.buenosayres.model.builder;

import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.model.Usuario;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class UserBuilder {
	private final Usuario usuario;
	public UserBuilder()
	{
		usuario = new Usuario();
	}
	public UserBuilder withId(UUID id)
	{
		usuario.setId(id);
		return this;
	}
	public UserBuilder withUsername(String username)
	{
		usuario.setUsername(username);
		return this;
	}
	public UserBuilder withPassword(String password)
	{
		usuario.setPassword(password);
		return this;
	}
	public UserBuilder isEnabled(boolean enabled)
	{
		usuario.setEnabled(enabled);
		return this;
	}
	public UserBuilder withRole(Rol rol)
	{
		usuario.addRol(rol);
		return this;
	}
	public UserBuilder withRoles(List<Rol> roles)
	{
		usuario.setRoles(new HashSet<>(roles));
		return this;
	}
	public Usuario build()
	{
		return usuario;
	}
}
