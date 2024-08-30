package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dao.interfaces.ProfesorDAO;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {
	ProfesorDAO profesorDAO;
	PersonaService personaService;
	UserService userService;
	@Autowired
	public ProfesorService(ProfesorDAO profesorDAO, PersonaService personaService, UserService userService)
	{
		this.profesorDAO = profesorDAO;
		this.personaService = personaService;
		this.userService = userService;
	}

	public List<Profesor> get()
	{
		return profesorDAO.findAll();
	}
	@Transactional
	public Profesor create(Profesor profesor) throws ServiceException
	{
		// Verificar que el profesor no esté ya registrado
		if (profesorDAO.findByPersonaDni(profesor.getPersona().getDni()).isPresent())
			throw new ServiceException("Profesor ya existente", 400);
		// Crear persona si no existe o resolverla
		profesor.setPersona(personaService.create(profesor.getPersona()));
		// Obtener usuario/crearlo
		Usuario usuario = userService.create(profesor.getPersona().getId());
		profesor.getPersona().setUsuario(usuario);
		// Agregar rol..
		userService.giveRole(usuario, profesor.getTipo());
		return profesorDAO.save(profesor);
	}

	public List<Profesor> get(String rol, Nivel nivel)
	{
		return profesorDAO.findProfesoresByRolAndNivel(rol, nivel);
	}
}
