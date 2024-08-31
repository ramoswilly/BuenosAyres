package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.EvaluacionDAO;
import org.gamma.buenosayres.dao.interfaces.MateriaDAO;
import org.gamma.buenosayres.dao.interfaces.ProfesorDAO;
import org.gamma.buenosayres.dto.EvaluacionDTO;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.model.Materia;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EvaluacionService {
	private MateriaDAO materiaDAO;
	private EvaluacionDAO evaluacionDAO;
	private ProfesorDAO profesorDAO;
	@Autowired
	public EvaluacionService(MateriaDAO materiaDAO, EvaluacionDAO evaluacionDAO, ProfesorDAO profesorDAO)
	{
		this.materiaDAO = materiaDAO;
		this.evaluacionDAO = evaluacionDAO;
		this.profesorDAO = profesorDAO;
	}
	public List<Evaluacion> get(UUID idMateria) throws ServiceException
	{
		Optional<Materia> materia = materiaDAO.findById(idMateria);
		if (materia.isEmpty()) throw new ServiceException("Materia inexistente", 404);
		return evaluacionDAO.findByMateriaOrderByFechaCreacion(materia.get());
	}
	public Evaluacion create(EvaluacionDTO evaluacionDTO) throws ServiceException
	{
		// verificar materia
		Optional<Materia> materia = materiaDAO.findById(evaluacionDTO.getIdMateria()); //Verificar null
		if (materia.isEmpty()) throw new ServiceException("Materia inexistente", 404);
		// verificar profesor
		//Optional<Profesor> profesor = profesorDAO.findById(evaluacionDTO.getIdProfesor());
		//if (profesor.isEmpty()) throw new ServiceException("Profesor inexistente", 404);
		// ok crear
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setDescripcion(evaluacionDTO.getDescripcion());
		evaluacion.setComentarios(evaluacionDTO.getComentarios());
		evaluacion.setMateria(materia.get());
		//evaluacion.setProfesor(profesor.get());
		evaluacion.setFechaCreacion(new Date());
		evaluacion.setFechaVencimiento(evaluacionDTO.getFechaVencimiento());
		return evaluacionDAO.save(evaluacion);
	}
}
