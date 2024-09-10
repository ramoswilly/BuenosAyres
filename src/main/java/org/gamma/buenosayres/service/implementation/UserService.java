package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dao.interfaces.PersonaDAO;
import org.gamma.buenosayres.dao.interfaces.RolDAO;
import org.gamma.buenosayres.dao.interfaces.UsuarioDAO;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
	private UsuarioDAO usuarioDAO;
	private RolDAO rolDAO;
	private PersonaDAO personaDAO;
	@Autowired
	public UserService(UsuarioDAO usuarioDAO, RolDAO rolDAO, PersonaDAO personaDAO)
	{
		this.usuarioDAO = usuarioDAO;
		this.rolDAO = rolDAO;
		this.personaDAO = personaDAO;
	}
	@Transactional
	public Usuario create(UUID id) throws ServiceException //TODO: excepcion
	{
		Optional<Persona> persona = personaDAO.findById(id);
		if (persona.isEmpty())
			throw new ServiceException("Intento de crear usuario para persona inexistente", 400);
		Optional<Usuario> existing = usuarioDAO.findByPersonaId(id);
		if (existing.isPresent()) return existing.get();

		Usuario usuario = new Usuario();
		usuario.setUsername(persona.get().getDni());
		usuario.setPassword(persona.get().getDni()); //TODO: password
		usuario.setEnabled(true);
		usuario.setPersona(persona.get());
		return usuarioDAO.save(usuario);
	}
	@Transactional
	public Usuario giveRole(Usuario usuario, String name) throws ServiceException
	{
		Optional<Rol> byAuthority = rolDAO.findByAuthority(String.valueOf(name));
		if (byAuthority.isEmpty()) throw new ServiceException("Rol inexistente", 400);
		usuario.addRol(byAuthority.get());
		return usuarioDAO.save(usuario);
	}
	@Transactional
	public boolean hasRole(Usuario usuario, String name)
	{
		Optional<Rol> byAuthority = rolDAO.findByAuthority(String.valueOf(name));
		if (byAuthority.isEmpty()) return false;
		return usuario.getRoles().stream().anyMatch(rol -> rol.equals(byAuthority.get()));
	}
	public Optional<Usuario> get(String username)
	{
		return usuarioDAO.findByUsername(username);
	}
}
