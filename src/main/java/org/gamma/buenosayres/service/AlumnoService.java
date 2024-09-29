package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.*;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlumnoService {
	private final AlumnoRepository alumnoRepository;
	private final CursoDAO cursoDAO;
	private final PersonaService personaService;
	private final UserService userService;
	private final SaludDAO saludDAO;
	private final UsuarioDAO usuarioDAO;
	private final PadreDAO padreDAO;
	@Autowired
	public AlumnoService(AlumnoRepository alumnoRepository, CursoDAO cursoDAO, PersonaService personaService, UserService userService, SaludDAO saludDAO, UsuarioDAO usuarioDAO, PadreDAO padreDAO)
	{
		this.alumnoRepository = alumnoRepository;
		this.cursoDAO = cursoDAO;
		this.personaService = personaService;
		this.userService = userService;
		this.saludDAO = saludDAO;
		this.usuarioDAO = usuarioDAO;
		this.padreDAO = padreDAO;
	}
	@Transactional
	public Alumno newAlumno(Alumno alumno) throws ServiceException
	{
		// Revisar que no exista el alumno
		if (alumnoRepository.findAlumnoByPersona_Dni(alumno.getPersona().getDni()).isPresent())
			throw new ServiceException("Alumno ya registrado\n", 400);
		// Revisar que exista curso
		Curso curso = cursoDAO.findById(alumno.getCurso().getId()).orElseThrow(
				()->new ServiceException("Curso inexistente\n", 400)
		);
		// Crear persona si no existe
		alumno.setPersona(personaService.create(alumno.getPersona()));
		// Crear su ficha de salud
		if (saludDAO.findByPersona(alumno.getPersona()).isEmpty()) {
			Salud salud = new Salud();
			salud.setPersona(alumno.getPersona());
			saludDAO.save(salud);
		}
		// Obtener usuario/crearlo
		Usuario usuario = userService.create(alumno.getPersona().getId());
		alumno.getPersona().setUsuario(usuario);
		// Agregar rol..
		userService.giveRole(usuario, "ROLE_ALUMNO");
		// Habilitado..
		alumno.setHabilitado(true);
		return alumnoRepository.save(alumno);
	}

	public List<Alumno> findAll()
	{
		return alumnoRepository.findAll();
	}

	public void updateAlumno(Alumno updatedAlumno) throws ServiceException {
		Optional<Alumno> alumnoOptional = alumnoRepository.findById(updatedAlumno.getId());
		if (alumnoOptional.isEmpty()) {
			throw new ServiceException("Alumno inexistente\n", 400);
		}

		Alumno existingAlumno = alumnoOptional.get();

		// Se está habilitando/deshabilitando
		if (updatedAlumno.isHabilitado() != existingAlumno.isHabilitado()) {
			existingAlumno.setHabilitado(updatedAlumno.isHabilitado());
			userService.enable(existingAlumno.getPersona().getUsuario(), updatedAlumno.isHabilitado());
		}

		if (existingAlumno.getPersona() != null) {
			Optional<Alumno> alumnoByPersonaDni = alumnoRepository.findAlumnoByPersona_Dni(updatedAlumno.getPersona().getDni());
			if (alumnoByPersonaDni.isPresent() && alumnoByPersonaDni.get().getId() != updatedAlumno.getId())
				throw new ServiceException("No se puede actualizar el DNI, corresponde a otro alumno\n", 400);
		}

		if (updatedAlumno.getCurso() != null && updatedAlumno.getCurso().getId() != null) {
			Curso curso = cursoDAO.findById(updatedAlumno.getCurso().getId())
					.orElseThrow(() -> new ServiceException("Curso inexistente\n", 400));
			existingAlumno.setCurso(curso);
		}

		if (updatedAlumno.getPersona() != null) {
			Persona updatedPersona = updatedAlumno.getPersona();
			if (updatedPersona.getDni() != null) {
				existingAlumno.getPersona().getUsuario().setUsername(updatedPersona.getDni());
				existingAlumno.getPersona().getUsuario().setPassword(updatedPersona.getDni());
				usuarioDAO.save(existingAlumno.getPersona().getUsuario());
				existingAlumno.getPersona().setDni(updatedPersona.getDni());
			}
			if (updatedPersona.getNombre() != null) {
				existingAlumno.getPersona().setNombre(updatedPersona.getNombre());
			}
			if (updatedPersona.getApellido() != null) {
				existingAlumno.getPersona().setApellido(updatedPersona.getApellido());
			}
			if (updatedPersona.getDireccion() != null) {
				existingAlumno.getPersona().setDireccion(updatedPersona.getDireccion());
			}
		}

		// Save the updated Alumno
		alumnoRepository.save(existingAlumno);
	}

	public Alumno getAlumno(UUID idAlumno) throws ServiceException
	{
		return alumnoRepository.findById(idAlumno).orElseThrow(() -> new ServiceException("No encontrado", 404));
	}

	public List<Alumno> findByCurso(UUID cursoId) throws ServiceException {
		Optional<Curso> curso = cursoDAO.findById(cursoId);
		if (curso.isEmpty()) {
			throw new ServiceException("Curso inexistente", 404);
		}
		return curso.get().getAlumnos();
	}

	public List<Alumno> findByPadre(Authentication authentication) throws ServiceException
	{
		Padre padre = padreDAO.findPadreByPersona_Dni(authentication.getName()).orElseThrow(() -> new ServiceException("Padre no encontrado", 404));
		return alumnoRepository.findAllByPersona_Familia(padre.getPersona().getFamilia());
	}
}
