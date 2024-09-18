package org.gamma.buenosayres.service;

import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.CursoDAO;
import org.gamma.buenosayres.repository.ProfesorDAO;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CursoService {
	private final CursoDAO cursoDAO;
	private final ProfesorDAO profesorDAO;
	private final AlumnoRepository alumnoRepository;
	@Autowired
	public CursoService(CursoDAO cursoDAO, ProfesorDAO profesorDAO, AlumnoRepository alumnoRepository)
	{
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
		this.alumnoRepository = alumnoRepository;
	}
	public List<Curso> findAll()
	{
		return cursoDAO.findAll();
	}

	public Curso asignarProfesor(UUID id, ProfesorDTO dto) throws ServiceException
	{
		Optional<Curso> curso = cursoDAO.findById(id);
		if (curso.isEmpty()) throw new ServiceException("Curso inexistente", 404);
		Optional<Profesor> profesor = profesorDAO.findById(dto.getId());
		if (profesor.isEmpty()) throw new ServiceException("Profesor inexistente", 404);
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
}
