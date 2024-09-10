package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.EntregaDAO;
import org.gamma.buenosayres.dao.interfaces.EvaluacionDAO;
import org.gamma.buenosayres.dto.EntregaDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Entrega;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EntregaService {
	private EntregaDAO entregaDAO;
	private EvaluacionDAO evaluacionDAO;
	private AlumnoDAO alumnoDAO;

	@Autowired
	public EntregaService(EntregaDAO entregaDAO, EvaluacionDAO evaluacionDAO, AlumnoDAO alumnoDAO) {
		this.entregaDAO = entregaDAO;
		this.evaluacionDAO = evaluacionDAO;
		this.alumnoDAO = alumnoDAO;
	}
	public void createPendingDeliveries(Evaluacion evaluacion)
	{
		//Crear objetos entrega pendientes
		evaluacion.getMateria().getCurso().getAlumnos()
				.stream()
				.map(alumno -> {
					Entrega pendiente = new Entrega();
					pendiente.setEvaluacion(evaluacion);
					pendiente.setAlumno(alumno);
					return pendiente;
				})
				.forEach(entrega -> entregaDAO.save(entrega));
	}
	public Entrega create(EntregaDTO entregaDTO) throws ServiceException {
		// verificar evaluacion
		Optional<Evaluacion> evaluacion = evaluacionDAO.findById(entregaDTO.getIdEvaluacion());
		if (evaluacion.isEmpty()) throw new ServiceException("Evaluacion inexistente", 404);
		// verificar alumno
		Optional<Alumno> alumno = alumnoDAO.findById(entregaDTO.getIdAlumno());
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		// ok crear
		Entrega entrega = new Entrega();
		entrega.setEvaluacion(evaluacion.get());
		entrega.setAlumno(alumno.get());
		entrega.setComentarios(entregaDTO.getComentarios());
		entrega.setFecha(new Date());
		return entregaDAO.save(entrega);
	}
	public List<Entrega> getByEvaluacion(UUID idEvaluacion) throws ServiceException {
		Optional<Evaluacion> evaluacion = evaluacionDAO.findById(idEvaluacion);
		if (evaluacion.isEmpty()) {
			throw new ServiceException("Evaluacion inexistente", 404);
		}
		// Entregas de la evaluacion
		return entregaDAO.findByEvaluacion(evaluacion.get());
	}
}