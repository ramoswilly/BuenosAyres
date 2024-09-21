package org.gamma.buenosayres.service;

import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.EntregaDAO;
import org.gamma.buenosayres.repository.EvaluacionDAO;
import org.gamma.buenosayres.dto.EntregaDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Entrega;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class EntregaService {
	private EntregaDAO entregaDAO;
	private EvaluacionDAO evaluacionDAO;
	private AlumnoRepository alumnoRepository;

	@Autowired
	public EntregaService(EntregaDAO entregaDAO, EvaluacionDAO evaluacionDAO, AlumnoRepository alumnoRepository) {
		this.entregaDAO = entregaDAO;
		this.evaluacionDAO = evaluacionDAO;
		this.alumnoRepository = alumnoRepository;
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
	public Entrega create(Authentication authentication, EntregaDTO entregaDTO) throws ServiceException {
		// verificar evaluacion
		Optional<Evaluacion> evaluacion = evaluacionDAO.findById(entregaDTO.getIdEvaluacion());
		if (evaluacion.isEmpty()) throw new ServiceException("Evaluacion inexistente", 404);
		// verificar alumno
		if (authentication.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ALUMNO")))
			throw new ServiceException("Accion no permitida", 403);
		Optional<Alumno> alumno = alumnoRepository.findAlumnoByPersona_Dni(authentication.getName());
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		// Verificar si entrega ya existe...
		Optional<Entrega> entregaExistente = entregaDAO.findByAlumnoAndEvaluacion(alumno.get(), evaluacion.get());
		if (entregaExistente.isPresent()) throw new ServiceException("Entrega ya realizada", 403);

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
		// Obtener alumnos de la materia
		List<Alumno> alumnos = evaluacion.get().getMateria().getCurso().getAlumnos();

		// Objetos de entrega
		List<Entrega> entregasRealizadas = entregaDAO.findByEvaluacion(evaluacion.get()); //Ya entregados
		//Sin entregar
		List<Entrega> entregasSinRealizar = alumnos.stream().filter(alumno -> entregasRealizadas
						.stream()
						.noneMatch(entrega -> entrega.getAlumno().equals(alumno)))
				.map(alumno -> {
					Entrega entregaSinRealizar = new Entrega();
					entregaSinRealizar.setAlumno(alumno);
					entregaSinRealizar.setEvaluacion(evaluacion.get());
					return entregaSinRealizar;
				})
				.toList();

		//Mezclar
		return Stream.concat(entregasRealizadas.stream(), entregasSinRealizar.stream()).toList();

		// Entregas de la evaluacion
		//return entregaDAO.findByEvaluacion(evaluacion.get());
	}


	public Entrega getEntregaAlumno(String dniAlumno, UUID evaluacionId) throws ServiceException {
		// Obtener el alumno a partir del DNI
		Optional<Alumno> alumno = alumnoRepository.findAlumnoByPersona_Dni(dniAlumno);
		if (alumno.isEmpty()) {
			throw new ServiceException("Alumno no encontrado", 404);
		}

		// Obtener la evaluación
		Evaluacion evaluacion = evaluacionDAO.findById(evaluacionId)
				.orElseThrow(() -> new ServiceException("Evaluación no encontrada", 404));

		// Buscar la entrega del alumno
		Optional<Entrega> entregaOptional = entregaDAO.findByAlumnoAndEvaluacion(alumno.get(), evaluacion);
		if (entregaOptional.isPresent()) {
			// Devolver la entrega si existe
			return entregaOptional.get();
		} else {
			// No se encontró la entrega
			throw new ServiceException("Entrega no encontrada para el alumno", 404);
		}
	}
}