package org.gamma.buenosayres.service;

import org.gamma.buenosayres.dto.CursoProblematicoDTO;
import org.gamma.buenosayres.dto.CursoRendimientoDTO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.*;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CursoService {
	private final CursoDAO cursoDAO;
	private final ProfesorDAO profesorDAO;
	private final AlumnoRepository alumnoRepository;
	private final CalificacionRepository calificacionRepository;
	private final SancionDAO sancionDAO;
	@Autowired
	public CursoService(CursoDAO cursoDAO, ProfesorDAO profesorDAO, AlumnoRepository alumnoRepository, CalificacionRepository calificacionRepository, SancionDAO sancionDAO)
	{
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
		this.alumnoRepository = alumnoRepository;
		this.calificacionRepository = calificacionRepository;
		this.sancionDAO = sancionDAO;
	}
	public List<Curso> findAll()
	{
		return cursoDAO.findAll();
	}

	public List<CursoRendimientoDTO> getRendimientoPorCurso() throws ServiceException {
		int currentYear = LocalDate.now().getYear();

		// Obtener calificaciones de primaria y secundaria del año actual
		List<Calificacion> calificaciones = calificacionRepository.findAll().stream()
				.filter(calificacion -> calificacion.getEvaluacion().getFechaCreacion().getYear() == currentYear
						&& calificacion.getEvaluacion().getMateria().getCurso().getNivel() != Nivel.INICIAL)
				.filter(calificacion -> calificacion.getAlumno().isHabilitado())
				.toList();

		// Agrupar por curso y calcular el promedio
		Map<Curso, Double> promediosPorCurso = calificaciones.stream()
				.collect(Collectors.groupingBy(c -> c.getEvaluacion().getMateria().getCurso(),
						Collectors.averagingDouble(Calificacion::getNota)));

		// Crear la lista de DTOs
		return promediosPorCurso.entrySet().stream()
				.map(entry -> new CursoRendimientoDTO(entry.getKey(), entry.getValue().floatValue()))
				.toList();
	}
	public Curso asignarProfesor(UUID id, ProfesorDTO dto) throws ServiceException
	{
		Optional<Curso> curso = cursoDAO.findById(id);
		if (curso.isEmpty()) throw new ServiceException("Curso inexistente", 404);
		Optional<Profesor> profesor = profesorDAO.findById(dto.getId());
		if (profesor.isEmpty()) throw new ServiceException("Profesor inexistente", 404);
		if (!profesor.get().isHabilitado()) throw new ServiceException("Profesor deshabilitado", 404);
		// remover responsable actual
		if (curso.get().getResponsable() != null) {
			curso.get().getResponsable().getCursos().remove(curso.get());
		}
		// agregar nuevo
		if (profesor.get().getCursos() == null) {
			profesor.get().setCursos(new ArrayList<>());
		}
		profesor.get().getCursos().add(curso.get());
		curso.get().setResponsable(profesor.get());
		//guardar profe
		profesorDAO.save(profesor.get());
		//guardar curso
		return cursoDAO.save(curso.get());
	}

	public List<Curso> findByAlumno(String dni) throws ServiceException
	{
		Optional<Alumno> alumno = alumnoRepository.findAlumnoByPersona_Dni(dni);
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		return cursoDAO.findByAlumno(alumno.get());
	}

	public List<Curso> findByDirector(String username) throws ServiceException {
		Optional<Profesor> profesor = profesorDAO.findByPersona_Usuario_Username(username);
		if (profesor.isEmpty()) {
			throw new ServiceException("Profesor inexistente", 404);
		}
		return cursoDAO.findAll().stream()
				.filter(curso -> curso.getNivel() == profesor.get().getNivel())
				.toList();
	}

	public List<Curso> findByAlumno(UUID alumnoId) throws ServiceException
	{
		Optional<Alumno> alumno = alumnoRepository.findById(alumnoId);
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		return cursoDAO.findByAlumno(alumno.get());
	}

	public List<CursoProblematicoDTO> getCursosProblematicos() throws ServiceException {
		int currentYear = LocalDate.now().getYear();

		// Obtener sanciones del año actual
		List<Sancion> sanciones = sancionDAO.findAll().stream()
				.filter(sancion -> sancion.getFecha().getYear() == currentYear)
				.filter(sancion -> sancion.getAlumno().isHabilitado())
				.toList();

		// Agrupar por curso y contar las sanciones
		Map<Curso, Long> sancionesPorCurso = sanciones.stream()
				.collect(Collectors.groupingBy(s -> s.getAlumno().getCurso(), Collectors.counting()));

		// Crear la lista de DTOs ordenados por cantidad de sanciones
		return sancionesPorCurso.entrySet().stream()
				.map(entry -> new CursoProblematicoDTO(entry.getKey(), entry.getValue()))
				.sorted(Comparator.comparingLong(CursoProblematicoDTO::getSanciones).reversed())
				.limit(5)
				.toList();
	}
	public Curso habilitar(UUID idCurso) throws ServiceException
	{
		Curso curso = cursoDAO.findById(idCurso).orElseThrow(() -> new ServiceException("Curso inexistente", 404));
		curso.setHabilitado(!curso.isHabilitado());
		return cursoDAO.save(curso);
	}

	public List<Curso> findByPreceptor(String username) throws ServiceException
	{
		Optional<Profesor> profesor = profesorDAO.findByPersona_Usuario_Username(username);
		if (profesor.isEmpty()) {
			throw new ServiceException("Preceptor inexistente", 404);
		}
		return profesor.get().getCursos();
	}
}
