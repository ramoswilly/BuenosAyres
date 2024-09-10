package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.CalificacionRepository;
import org.gamma.buenosayres.dao.interfaces.EntregaDAO;
import org.gamma.buenosayres.dao.interfaces.EvaluacionDAO;
import org.gamma.buenosayres.dto.CalificacionDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Calificacion;
import org.gamma.buenosayres.model.Entrega;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CalificacionService {
	@Autowired
	private EvaluacionDAO evaluacionDAO;
	@Autowired
	private AlumnoDAO alumnoDAO;
	@Autowired
	private CalificacionRepository calificacionRepository;
	public Calificacion update(CalificacionDTO dto) throws ServiceException
	{
		// Existe evaluacion?
		Optional<Evaluacion> evaluacion = evaluacionDAO.findById(dto.getIdEvaluacion());
		if (evaluacion.isEmpty()) throw new ServiceException("Evaluacion inexistente", 404);
		// Existe alumno?
		Optional<Alumno> alumno = alumnoDAO.findById(dto.getIdAlumno());
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		// Crear o actualizar objeto calificacion
		Optional<Calificacion> optionalCalificacion = calificacionRepository.findByEvaluacionAndAlumno(evaluacion.get(), alumno.get());
		Calificacion calificacion = optionalCalificacion.orElseGet(Calificacion::new);
		calificacion.setAlumno(alumno.get());
		calificacion.setEvaluacion(evaluacion.get());
		calificacion.setNota(dto.getNota());
		return calificacionRepository.save(calificacion);
	}

	public Calificacion get(UUID evaluacionId, UUID alumnoId) throws ServiceException
	{
		Optional<Evaluacion> evaluacion = evaluacionDAO.findById(evaluacionId);
		if (evaluacion.isEmpty()) throw new ServiceException("Evaluacion inexistente", 404);
		Optional<Alumno> alumno = alumnoDAO.findById(alumnoId);
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		return calificacionRepository.findByEvaluacionAndAlumno(evaluacion.get(), alumno.get()).orElseGet(Calificacion::new);
	}
}
