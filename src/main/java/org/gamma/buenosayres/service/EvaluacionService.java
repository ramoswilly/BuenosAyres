package org.gamma.buenosayres.service;

import org.gamma.buenosayres.repository.EvaluacionDAO;
import org.gamma.buenosayres.repository.MateriaDAO;
import org.gamma.buenosayres.repository.ProfesorDAO;
import org.gamma.buenosayres.dto.EvaluacionDTO;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.model.Materia;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
	public Evaluacion getById(UUID id) throws ServiceException {
		return evaluacionDAO.findById(id).orElseThrow(() -> new ServiceException("Evaluacion inexistente", 404));
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
		evaluacion.setHabilitada(true);

		evaluacionDAO.save(evaluacion);

		// Crear los objetos de entrega pendiente
		//entregaService.createPendingDeliveries(evaluacion);

		return evaluacion;
	}

	public List<Evaluacion> getEntregables(UUID idMateria) throws ServiceException
	{
		Optional<Materia> materia = materiaDAO.findById(idMateria);
		if (materia.isEmpty()) throw new ServiceException("Materia inexistente", 404);
		return evaluacionDAO.findEntregablesByMateria(materia.get(), LocalDate.now());
	}
	public Evaluacion update(UUID id, EvaluacionDTO dto) throws ServiceException {
		// Obtener la evaluación existente
		Evaluacion evaluacionExistente = getById(id);

		// Actualizar campos si es necesario
		boolean updated = false;
		EvaluacionDTO responseDTO = new EvaluacionDTO();
		if (dto.getDescripcion() != null) {
			evaluacionExistente.setDescripcion(dto.getDescripcion());
			responseDTO.setDescripcion(dto.getDescripcion());
			updated = true;
		}
		if (dto.getComentarios() != null) {
			evaluacionExistente.setComentarios(dto.getComentarios());
			responseDTO.setComentarios(dto.getComentarios());
			updated = true;
		}
		if (dto.getFechaVencimiento() != null) {
			evaluacionExistente.setFechaVencimiento(dto.getFechaVencimiento());
			responseDTO.setFechaVencimiento(dto.getFechaVencimiento());
			updated = true;
		}
		if (dto.isHabilitada() != evaluacionExistente.isHabilitada()) { // Comparar booleanos
			evaluacionExistente.setHabilitada(dto.isHabilitada());
			responseDTO.setHabilitada(dto.isHabilitada());
			updated = true;
		}
		// Guardar la evaluación actualizada solo si hubo cambios
		if (updated) {
			evaluacionDAO.save(evaluacionExistente);
		}

		return evaluacionExistente;
	}

}
