package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dto.ActualizarFamiliaDTO;
import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.dao.interfaces.FamiliaDAO;
import org.gamma.buenosayres.dao.interfaces.PersonaDAO;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	@Transactional
	public void actualizarFamilia(Familia actualizar) throws ServiceException
	{
		Familia familiaSync = familiaDAO.findById(actualizar.getId()).orElseThrow(() -> new ServiceException("Familia Inexistente", 400));

		/* Remove previous family members */
		for (Persona persona : personaDAO.findPersonaByFamilia(familiaSync)) {
			persona.setFamilia(null);
			personaDAO.save(persona);
		}

		/* Set new family members */
		for (Persona persona : actualizar.getMiembros()) {
			Persona personaSync = personaDAO.findById(persona.getId()).orElseThrow(() -> new ServiceException(String.format("Persona Inexistente: %s", persona.getId()), 400));
			personaSync.setFamilia(familiaSync);
			familiaSync.getMiembros().add(personaSync);
		}

		/* Update apellido if necessary */
		if (actualizar.getApellido() != null) familiaSync.setApellido(actualizar.getApellido());

		/* Finally Persist */
		familiaDAO.save(familiaSync);
	}
	public void agregarMiembros(Familia actualizar) throws ServiceException
	{
		Familia familiaSync = familiaDAO.findById(actualizar.getId()).orElseThrow(() -> new ServiceException("Familia Inexistente", 400));

		for (Persona persona : actualizar.getMiembros()) {
			Persona personaSync = personaDAO.findById(persona.getId()).orElseThrow(() -> new ServiceException(String.format("Persona Inexistente: %s", persona.getId()), 400));
			personaSync.setFamilia(familiaSync);
			familiaSync.getMiembros().add(personaSync);
		}

		familiaDAO.save(familiaSync);
	}

	public void removerMiembro(UUID familia, UUID persona) throws ServiceException
	{
		Familia familiaSync = familiaDAO.findById(familia).orElseThrow(() -> new ServiceException("Familia Inexistente", 400));
		Persona personaSync = personaDAO.findById(persona).orElseThrow(() -> new ServiceException(String.format("Persona Inexistente: %s", persona), 400));

		personaSync.setFamilia(null);
		personaDAO.save(personaSync);
	}

	public List<Persona> getMiembrosOf(UUID familia) throws ServiceException
	{
		Familia familiaHibernate = familiaDAO.findById(familia).orElseThrow(() -> new ServiceException("Familia Inexistente", 400));
		return familiaHibernate.getMiembros();
	}
}
