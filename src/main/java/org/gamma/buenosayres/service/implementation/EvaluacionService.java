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

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EvaluacionService {
	private MateriaDAO materiaDAO;
	private EvaluacionDAO evaluacionDAO;
	private ProfesorDAO profesorDAO;
	private EntregaService entregaService;
	@Autowired
	public EvaluacionService(MateriaDAO materiaDAO, EvaluacionDAO evaluacionDAO, ProfesorDAO profesorDAO, EntregaService entregaService)
	{
		this.materiaDAO = materiaDAO;
		this.evaluacionDAO = evaluacionDAO;
		this.profesorDAO = profesorDAO;
		this.entregaService = entregaService;
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
		evaluacion.setFechaCreacion(LocalDate.now());
		evaluacion.setFechaVencimiento(evaluacionDTO.getFechaVencimiento());
		return evaluacionDAO.save(evaluacion);
	}

	public Evaluacion create(String username, EvaluacionDTO evaluacionDTO) throws ServiceException
	{
		Optional<Materia> materia = materiaDAO.findById(evaluacionDTO.getIdMateria()); //Verificar null
		if (materia.isEmpty()) throw new ServiceException("Materia inexistente", 404);
		Optional<Profesor> profesor = profesorDAO.findByPersona_Usuario_Username(username);
		if (profesor.isEmpty()) throw new ServiceException("Profesor inexistente", 404);
		if (!profesor.get().getMaterias().contains(materia.get())) throw new ServiceException("Materia no asignada", 403);

		// Crear la evaluacion
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setDescripcion(evaluacionDTO.getDescripcion());
		evaluacion.setComentarios(evaluacionDTO.getComentarios());
		evaluacion.setMateria(materia.get());
		evaluacion.setProfesor(profesor.get());
		evaluacion.setFechaCreacion(LocalDate.now());
		evaluacion.setFechaVencimiento(evaluacionDTO.getFechaVencimiento());

		evaluacionDAO.save(evaluacion);

		// Crear los objetos de entrega pendiente
		entregaService.createPendingDeliveries(evaluacion);

		return evaluacion;
	}

	public List<Evaluacion> getEntregables(UUID idMateria) throws ServiceException
	{
		Optional<Materia> materia = materiaDAO.findById(idMateria);
		if (materia.isEmpty()) throw new ServiceException("Materia inexistente", 404);
		return evaluacionDAO.findEntregablesByMateria(materia.get(), LocalDate.now());
	}
}
