package org.gamma.buenosayres.service;

import org.gamma.buenosayres.dto.SaludDTO;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaludService {
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private SaludDAO saludDAO;
	@Autowired
	private ProfesorDAO profesorDAO;
	@Autowired
	private PersonaDAO personaDAO;
	@Autowired
	private CursoDAO cursoDAO;
	@Autowired
	private PatologiaDAO patologiaDAO;

	public List<Salud> obtenerSalud(Authentication authentication) {
		if (authenticationService.hasRole(authentication, "ROLE_PRECEPTOR")) {
			if (profesorDAO.findByPersonaDni(authentication.getName()).isPresent()) {
				return profesorDAO.findByPersonaDni(authentication.getName()).get().getCursos().stream()
						.flatMap(curso -> curso.getAlumnos().stream())
						.map(alumno -> saludDAO.findByPersona(alumno.getPersona()))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.collect(Collectors.toList());
			}
		}
		return personaDAO.findByRoles(Set.of("ROLE_PROFESOR", "ROLE_ALUMNO"))
				.stream()
				.filter(persona -> persona.getUsuario().isEnabled())
				.map(persona -> saludDAO.findByPersona(persona))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	public List<Salud> obtenerSaludPorPreceptor(String username) {
		return personaDAO.findByRoles(Set.of("ROLE_PROFESOR", "ROLE_ALUMNO"))
				.stream()
				.filter(persona -> persona.getUsuario().isEnabled())
				.map(persona -> saludDAO.findByPersona(persona))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
		/*
		Optional<Profesor> profesor = profesorDAO.findByPersonaDni(username);
		if (profesor.isPresent()) {
			List<Curso> cursos = cursoDAO.findAll().stream()
					.filter(curso -> curso.getResponsable() != null)
					.filter(curso -> curso.getResponsable().equals(profesor.get()))
					.toList();

			return cursos.stream()
					.flatMap(curso -> curso.getAlumnos().stream())
					.map(Alumno::getPersona)
					.map(persona -> saludDAO.findById(persona.getId()))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toList());
		}
		return List.of();*/
	}

	public Salud obtenerSaludPorId(UUID idPersona) throws ServiceException
	{
		return saludDAO.findById(idPersona).orElseThrow(() -> new ServiceException("No se encontró información de salud para la persona", 404));
	}
	private Patologia buscarPatologia(Patologia patologia) throws ServiceException {
		if (patologia.getId() != null) {
			Optional<Patologia> patologiaExistente = patologiaDAO.findById(patologia.getId());
			if (patologiaExistente.isPresent()) {
				return patologiaExistente.get();
			}
		}
		throw new ServiceException("Patología inexistente", 404);
	}
	public Salud actualizarSalud(UUID idPersona, SaludDTO saludDTO) throws ServiceException {
		Optional<Salud> saludOptional = saludDAO.findById(idPersona);
		if (saludOptional.isEmpty()) {
			throw new ServiceException("No se encontró información de salud para la persona", 404);
		}
		Salud salud = saludOptional.get();
		Optional<Persona> persona = personaDAO.findById(idPersona);
		if (persona.isEmpty()) throw new ServiceException("No se encontró la persona", 404);
		salud.setObraSocial(saludDTO.getObraSocial());
		salud.setNumeroAfiliado(saludDTO.getNumeroAfiliado());
		List<Patologia> patologias = new ArrayList<>();
		if (saludDTO.getPatologias() != null) {
			for (Patologia patologia : saludDTO.getPatologias())
				patologias.add(buscarPatologia(patologia));
		} else {
			salud.getPatologias().clear();
		}
		patologias.forEach(System.out::println);
		salud.setPatologias(patologias);
		return saludDAO.save(salud);
	}
}