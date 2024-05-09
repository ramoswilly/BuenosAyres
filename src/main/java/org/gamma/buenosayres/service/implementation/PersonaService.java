package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.PersonaDAO;
import org.gamma.buenosayres.dto.ListarPersonaDTO;
import org.gamma.buenosayres.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
