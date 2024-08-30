package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.CursoDAO;
import org.gamma.buenosayres.dao.interfaces.ProfesorDAO;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoService {
	private final CursoDAO cursoDAO;
	private final ProfesorDAO profesorDAO;
	@Autowired
	public CursoService(CursoDAO cursoDAO, ProfesorDAO profesorDAO)
	{
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
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
}
