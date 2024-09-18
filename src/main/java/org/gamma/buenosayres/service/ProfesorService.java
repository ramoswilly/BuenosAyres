package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dto.ProfesorRendimientoDTO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.CalificacionRepository;
import org.gamma.buenosayres.repository.ProfesorDAO;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.repository.SaludDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProfesorService {
	ProfesorDAO profesorDAO;
	SaludDAO saludDAO;
	PersonaService personaService;
	UserService userService;
	CalificacionRepository calificacionRepository;
	@Autowired
	public ProfesorService(ProfesorDAO profesorDAO, PersonaService personaService, SaludDAO saludDAO, UserService userService, CalificacionRepository calificacionRepository)
	{
		this.profesorDAO = profesorDAO;
		this.personaService = personaService;
		this.saludDAO = saludDAO;
		this.userService = userService;
		this.calificacionRepository = calificacionRepository;
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
		// Crear su ficha de salud
		if (saludDAO.findByPersona(profesor.getPersona()).isEmpty()) {
			Salud salud = new Salud();
			salud.setPersona(profesor.getPersona());
			saludDAO.save(salud);
		}
		// Obtener usuario/crearlo
		Usuario usuario = userService.create(profesor.getPersona().getId());
		profesor.getPersona().setUsuario(usuario);
		// Agregar rol..
		userService.giveRole(usuario, profesor.getTipo().name());
		return profesorDAO.save(profesor);
	}

	public List<ProfesorRendimientoDTO> getRendimientoPorProfesor() throws ServiceException {
		int currentYear = LocalDate.now().getYear();

		// Obtener calificaciones del año actual
		List<Calificacion> calificaciones = calificacionRepository.findAll().stream()
				.filter(calificacion -> calificacion.getEvaluacion().getFechaCreacion().getYear() == currentYear)
				.toList();

		// Agrupar por profesor y materia, luego calcular el promedio
		return calificaciones.stream()
				.collect(Collectors.groupingBy(c -> c.getEvaluacion().getMateria().getProfesor(),
						Collectors.groupingBy(c -> c.getEvaluacion().getMateria(),
								Collectors.averagingDouble(Calificacion::getNota))))
				.entrySet().stream()
				.flatMap(entryProfesor -> entryProfesor.getValue().entrySet().stream()
						.map(entryMateria -> new ProfesorRendimientoDTO(
								entryProfesor.getKey().getPersona().getNombre(),
								entryProfesor.getKey().getPersona().getApellido(),
								entryMateria.getKey().getNombre(),
								entryMateria.getKey().getCurso(),
								entryMateria.getValue().floatValue())))
				.toList();
	}
	public List<Profesor> get(String rol, Nivel nivel)
	{
		return profesorDAO.findProfesoresByRolAndNivel(rol, nivel);
	}
}
