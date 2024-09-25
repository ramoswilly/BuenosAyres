package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dto.MateriaRendimientoDTO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.*;
import org.gamma.buenosayres.dto.MateriaDTO;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MateriaService {
	private MateriaDAO materiaDAO;
	private CursoDAO cursoDAO;
	private ProfesorDAO profesorDAO;
	private UserService userService;
	private CalificacionRepository calificacionRepository;
	private AlumnoRepository alumnoRepository;
	@Autowired
	public MateriaService(MateriaDAO materiaDAO, CursoDAO cursoDAO, ProfesorDAO profesorDAO, UserService userService, CalificacionRepository calificacionRepository, AlumnoRepository alumnoRepository)
	{
		this.materiaDAO = materiaDAO;
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
		this.userService = userService;
		this.calificacionRepository = calificacionRepository;
		this.alumnoRepository = alumnoRepository;
	}
	public List<Materia> get()
	{
		return materiaDAO.findAll();
	}
	public Materia create(MateriaDTO dto) throws ServiceException
	{
		// verificar que existe curso
		Optional<Curso> curso = cursoDAO.findById(dto.getId_curso());
		if (curso.isEmpty()) {
			throw new ServiceException("Curso seleccionado inexistente", 400);
		}
		/*// verificar que existe el profe
		Optional<Profesor> profesor = profesorDAO.findById(dto.getId_profesor());
		if (profesor.isEmpty()) {
			throw new ServiceException("Profesor seleccionado inexistente", 400);
		}*/

		Materia materia = new Materia();
		materia.setNombre(dto.getNombre());
		materia.setHabilitada(true);
		materia.setCurso(curso.get());
		//materia.setProfesor(profesor.get());
		return materiaDAO.save(materia);
	}
	@Transactional
	public Materia asignarProfesor(UUID idMateria, ProfesorDTO dto) throws ServiceException
	{
		Optional<Materia> materia = materiaDAO.findById(idMateria);
		if (materia.isEmpty()) throw new ServiceException("Materia inexistente", 400);
		Optional<Profesor> profesor = profesorDAO.findById(dto.getId());
		if (profesor.isEmpty()) throw new ServiceException("Profesor inexistente", 400);
		if (profesor.get().getMaterias().stream().anyMatch(id -> id.getId().equals(idMateria))) throw new ServiceException("Profesor ya dicta materia", 400);
		if (materia.get().getProfesor() != null) { //cambiar el titular
			materia.get().getProfesor().getMaterias().remove(materia.get());
			materia.get().setProfesor(null);
		}
		materia.get().setProfesor(profesor.get());
		profesor.get().getMaterias().add(materia.get());
		return materiaDAO.save(materia.get());
	}

	public Materia get(UUID materia) throws ServiceException
	{
		return materiaDAO.findById(materia)
				.orElseThrow(() -> new ServiceException("Materia inexistente", 404));
	}

	public Materia update(MateriaDTO materia) throws ServiceException
	{
		Optional<Materia> byId = materiaDAO.findById(materia.getId());
		if (byId.isEmpty()) throw new ServiceException("Materia inexistente", 404);
		// Actualizar curso
		if (materia.getId_curso() != null) {
			Optional<Curso> curso = cursoDAO.findById(materia.getId_curso());
			if (curso.isEmpty()) throw new ServiceException("Curso inexistente", 404);
			byId.get().setCurso(curso.get());
		}
		//Está habilitada
		if (materia.isHabilitada() != byId.get().isHabilitada()) {
			byId.get().setHabilitada(materia.isHabilitada());
		}
		// Actualizar nombre
		if (materia.getNombre() != null) {
			byId.get().setNombre(materia.getNombre());
		}
		// Guardar
		return materiaDAO.save(byId.get());
	}

	public List<Materia> get(Authentication authentication)
	{
		// Es admin?
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			return materiaDAO.findAll();
		}
		// Es profesor?
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_PROFESOR"))) {
			// ¿Es profe de primaria?
			Optional<Profesor> profesor = profesorDAO.findByPersonaDni(authentication.getName());
			if (profesor.get().getNivel().equals(Nivel.PRIMARIA)) {
				return materiaDAO.findAllByCurso_Responsable(profesor.get());
			}
			// Obtener por profesor
			return materiaDAO.findAllByProfesor(profesor.get());
		}
		// Es alumno?
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ALUMNO"))) {
			// Convertir
			Optional<Alumno> alumnoByPersonaDni = alumnoRepository.findAlumnoByPersona_Dni(authentication.getName());
			return materiaDAO.findAllByCurso(alumnoByPersonaDni.get().getCurso()); //Materias de su curso
		}
		return new ArrayList<>(); //Nada
	}

	public List<MateriaRendimientoDTO> getRendimientoPorMateria() throws ServiceException {
		int currentYear = LocalDate.now().getYear();

		// Obtener las calificaciones del año actual
		List<Calificacion> calificaciones = calificacionRepository.findAll().stream()
				.filter(calificacion -> calificacion.getEvaluacion().getMateria().isHabilitada())
				.filter(calificacion -> calificacion.getEvaluacion().getFechaCreacion().getYear() == currentYear)
				.toList();

		// Agrupar por materia y calcular el promedio
		Map<Materia, Double> promediosPorMateria = calificaciones.stream()
				.collect(Collectors.groupingBy(c -> c.getEvaluacion().getMateria(),
						Collectors.averagingDouble(Calificacion::getNota)));

		// Crear la lista de DTOs
		return promediosPorMateria.entrySet().stream()
				.map(entry -> new MateriaRendimientoDTO(
						entry.getKey().getCurso(),
						entry.getKey().getNombre(),
						entry.getValue().floatValue()))
				.toList();
	}
}
