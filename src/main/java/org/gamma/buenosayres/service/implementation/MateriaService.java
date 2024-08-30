package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dao.interfaces.CursoDAO;
import org.gamma.buenosayres.dao.interfaces.MateriaDAO;
import org.gamma.buenosayres.dao.interfaces.ProfesorDAO;
import org.gamma.buenosayres.dto.MateriaDTO;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.model.Materia;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MateriaService {
	private MateriaDAO materiaDAO;
	private CursoDAO cursoDAO;
	private ProfesorDAO profesorDAO;
	@Autowired
	public MateriaService(MateriaDAO materiaDAO, CursoDAO cursoDAO, ProfesorDAO profesorDAO)
	{
		this.materiaDAO = materiaDAO;
		this.cursoDAO = cursoDAO;
		this.profesorDAO = profesorDAO;
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
		// Actualizar nombre
		if (materia.getNombre() != null) {
			byId.get().setNombre(materia.getNombre());
		}
		// Guardar
		return materiaDAO.save(byId.get());
	}
}
