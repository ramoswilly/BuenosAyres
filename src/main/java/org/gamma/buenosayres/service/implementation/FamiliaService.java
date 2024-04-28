package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.dao.interfaces.FamiliaDAO;
import org.gamma.buenosayres.dao.interfaces.PersonaDAO;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FamiliaService {
	private final FamiliaDAO familiaDAO;
	private final PersonaDAO personaDAO;
	@Autowired
	public FamiliaService(FamiliaDAO familiaDAO, PersonaDAO personaDAO)
	{
		this.familiaDAO = familiaDAO;
		this.personaDAO = personaDAO;
	}

	public List<Familia> getAll()
	{
		return familiaDAO.findAll();
	}

	public void newFamilia(String apellido)
	{
		Familia nuevaFamilia = new Familia();
		nuevaFamilia.setApellido(apellido);
		familiaDAO.save(nuevaFamilia);
	}

	public void addPersona(UUID familia, UUID persona) throws ServiceException
	{
		Familia agregarA = familiaDAO.findById(familia)
				.orElseThrow(() -> new ServiceException("Familia Inexistente", 400));
		Persona personaAgregar = personaDAO.findById(persona)
				.orElseThrow(() -> new ServiceException("Persona Inexistente", 400));
		agregarA.getMiembros().add(personaAgregar);
		personaAgregar.setFamilia(agregarA);
		familiaDAO.save(agregarA);
	}
}
