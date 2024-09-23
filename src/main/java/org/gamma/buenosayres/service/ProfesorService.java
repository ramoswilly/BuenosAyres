package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.dto.ProfesorRendimientoDTO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.CalificacionRepository;
import org.gamma.buenosayres.repository.ProfesorDAO;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.repository.SaludDAO;
import org.gamma.buenosayres.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfesorService {
	ProfesorDAO profesorDAO;
	SaludDAO saludDAO;
	PersonaService personaService;
	UserService userService;
	CalificacionRepository calificacionRepository;
	UsuarioDAO usuarioDAO;
	RolService rolService;
	@Autowired
	public ProfesorService(ProfesorDAO profesorDAO, PersonaService personaService, SaludDAO saludDAO, UserService userService, CalificacionRepository calificacionRepository, UsuarioDAO usuarioDAO, RolService rolService)
	{
		this.profesorDAO = profesorDAO;
		this.personaService = personaService;
		this.saludDAO = saludDAO;
		this.userService = userService;
		this.calificacionRepository = calificacionRepository;
		this.usuarioDAO = usuarioDAO;
		this.rolService = rolService;
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
		//Habilitar
		profesor.setHabilitado(true);
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

	public Profesor get(UUID idProfesor) throws ServiceException
	{
		return profesorDAO.findById(idProfesor).orElseThrow(() -> new ServiceException("Profesor inexistente", 404));
	}


	@Transactional
	public Profesor update(ProfesorDTO profesor) throws ServiceException {
		Optional<Profesor> existingProfesor = profesorDAO.findById(profesor.getId());
		if (existingProfesor.isEmpty()) {
			throw new ServiceException("Profesor no encontrado", 404);
		}
		Profesor toUpdate = existingProfesor.get();

		if (profesor.getDni() != null) {
			Optional<Profesor> profesorByPersonaDni = profesorDAO.findByPersonaDni(profesor.getDni());
			if (profesorByPersonaDni.isPresent() && profesorByPersonaDni.get().getId() != profesor.getId())
				throw new ServiceException("No se puede actualizar el DNI, corresponde a otro profesor", 400);
			toUpdate.getPersona().setDni(profesor.getDni());
			toUpdate.getPersona().getUsuario().setUsername(profesor.getDni());
			toUpdate.getPersona().getUsuario().setPassword(profesor.getDni());
			usuarioDAO.save(toUpdate.getPersona().getUsuario());
		}
		if (profesor.isHabilitado() != toUpdate.isHabilitado()) {
			toUpdate.setHabilitado(profesor.isHabilitado());
			userService.enable(toUpdate.getPersona().getUsuario(), toUpdate.isHabilitado());
		}
		if (profesor.getNombre() != null) {
			toUpdate.getPersona().setNombre(profesor.getNombre());
		}
		if (profesor.getApellido() != null) {
			toUpdate.getPersona().setApellido(profesor.getApellido());
		}
		if (profesor.getDireccion() != null) {
			toUpdate.getPersona().setDireccion(profesor.getDireccion());
		}
		// Actualizar otros campos
		if (profesor.getCuil() != null) {
			toUpdate.setCUIL(profesor.getCuil());
		}
		if (profesor.getTelefono() != null) {
			toUpdate.setTelefono(profesor.getTelefono());
		}
		if (profesor.getEmail() != null) {
			toUpdate.setEmail(profesor.getEmail());
		}
		if (profesor.getNivel() != null) {
			toUpdate.setNivel(profesor.getNivel());
		}
		if (profesor.getBanco() != null) {
			toUpdate.setBanco(profesor.getBanco());
		}
		if (profesor.getCbu() != null) {
			toUpdate.setCBU(profesor.getCbu());
		}
		if (profesor.getTipo() != null) {
			toUpdate.setTipo(profesor.getTipo());
			// Actualizar rol del usuario
			Usuario usuario = toUpdate.getPersona().getUsuario();
			if (usuario != null) {
				Rol rol = rolService.find(toUpdate.getTipo());
				toUpdate.getPersona().getUsuario().getRoles().remove(rol);
				//usuario.getRoles().clear(); //TODO: Alto bug
				userService.giveRole(usuario, profesor.getTipo().name());
			}
		}
		return profesorDAO.save(toUpdate);
	}
}
