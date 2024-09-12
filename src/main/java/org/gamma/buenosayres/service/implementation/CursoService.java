package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.CursoDAO;
import org.gamma.buenosayres.dao.interfaces.ProfesorDAO;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CursoService {
	private final CursoDAO cursoDAO;
	private final ProfesorDAO profesorDAO;
	private final AlumnoDAO alumnoDAO;
	@Autowired
	public CursoService(CursoDAO cursoDAO, ProfesorDAO profesorDAO, AlumnoDAO alumnoDAO)
	{
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
		this.alumnoDAO = alumnoDAO;
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
		Optional<Alumno> alumno = alumnoDAO.findAlumnoByPersona_Dni(dni);
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
}
