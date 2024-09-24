package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dto.UserDataDTO;
import org.gamma.buenosayres.repository.PersonaDAO;
import org.gamma.buenosayres.repository.RolDAO;
import org.gamma.buenosayres.repository.UsuarioDAO;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
	public Usuario update(UUID id) throws ServiceException
	{
		Optional<Persona> persona = personaDAO.findById(id);
		if (persona.isEmpty())
			throw new ServiceException("Intento de actualizar usuario para persona inexistente", 400);
		Optional<Usuario> existing = usuarioDAO.findByPersonaId(id);
		if (existing.isEmpty()) throw new ServiceException("Intento de actualizar usuario inexistente", 500);
		existing.get().setUsername(persona.get().getDni());
		existing.get().setPassword(persona.get().getDni());
		return usuarioDAO.save(existing.get());
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
	public boolean enable(Usuario usuario, boolean enable)
	{
		usuario.setEnabled(enable);
		usuarioDAO.save(usuario);
		return enable;
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

	public UserDataDTO get(Authentication authentication) throws ServiceException
	{
		Usuario usuario = usuarioDAO.findByUsername(authentication.getName()).orElseThrow(() -> new ServiceException("Usuario no encontrado", 404));
		UserDataDTO userData = new UserDataDTO();
		userData.setNombre(usuario.getPersona().getNombre());
		userData.setApellido(usuario.getPersona().getApellido());
		userData.setRol(switch (usuario.getRoles().stream().findAny().get().getAuthority()) {
			case "ROLE_ADMIN":
				yield "Administrador";
			case "ROLE_ALUMNO":
				yield "Alumno";
			case "ROLE_PADRE":
				yield "Padre";
			case "ROLE_PROFESOR":
				yield "Profesor";
			case "ROLE_PRECEPTOR":
				yield "Preceptor";
			case "ROLE_DIRECTOR":
				yield "Director";
			default:
				yield "Desconocido";
		});
		return userData;
	}
}
