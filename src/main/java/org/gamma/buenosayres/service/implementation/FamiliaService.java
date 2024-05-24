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
import java.util.Optional;
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
		nuevaFamilia.setHabilitada(true);
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
		Familia familia = familiaDAO.findById(actualizar.getId()).orElseThrow(() -> new ServiceException("Familia Inexistente", 400));
		/* Actualizar apellido */
		if (actualizar.getApellido() != null) familia.setApellido(actualizar.getApellido());
		/* ¿Está habilitada? TODO: Migrar a Boolean wrapper*/
		familia.setHabilitada(actualizar.isHabilitada());
		System.out.println(familia.isHabilitada());
		/* Finally Persist */
		familiaDAO.save(familia);
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

	public Optional<Familia> find(UUID idFamilia)
	{
		return familiaDAO.findById(idFamilia);
	}
}
