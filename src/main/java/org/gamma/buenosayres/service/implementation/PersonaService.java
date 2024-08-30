package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.PersonaDAO;
import org.gamma.buenosayres.dto.ListarPersonaDTO;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
	PersonaDAO personaDAO;
	@Autowired
	public PersonaService(PersonaDAO personaDAO)
	{
		this.personaDAO = personaDAO;
	}
	public List<Persona> getAll()
	{
		return personaDAO.findAll();
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
			// Guardado final
			//personaDAO.save(persona);
		}
		return persona;
	}
}
