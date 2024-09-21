package org.gamma.buenosayres.service;

import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.repository.PersonaDAO;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.repository.RolDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonaService {
	PersonaDAO personaDAO;
	RolDAO rolDAO;
	@Autowired
	public PersonaService(PersonaDAO personaDAO, RolDAO rolDAO)
	{
		this.personaDAO = personaDAO;
		this.rolDAO = rolDAO;
	}
	public List<Persona> getAll()
	{
		//return personaDAO.findAll();
		Set<String> rolSet = new HashSet<>();
		rolSet.add("ROLE_PADRE");
		rolSet.add("ROLE_ALUMNO");
		return personaDAO.findByRoles(rolSet);
	}
	public Persona create(Persona persona)
	{
		Optional<Persona> optional = personaDAO.findByDni(persona.getDni());
		if (optional.isPresent()) {
			persona = optional.get();
		} else {
			// Sincronizar con hibernate, obtener un id
			persona.setId(null);
			personaDAO.save(persona);
		}
		return persona;
	}
}
