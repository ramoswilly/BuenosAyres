package org.gamma.buenosayres.service;

import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.CalificacionRepository;
import org.gamma.buenosayres.repository.EvaluacionDAO;
import org.gamma.buenosayres.dto.*;
import org.gamma.buenosayres.mapper.AlumnoMapper;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Calificacion;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CalificacionService {
	@Autowired
	private EvaluacionDAO evaluacionDAO;
	@Autowired
	private AlumnoRepository alumnoRepository;
	@Autowired
	private AlumnoMapper alumnoMapper;
	@Autowired
	private CalificacionRepository calificacionRepository;
	public Calificacion update(CalificacionDTO dto) throws ServiceException
	{
		// Existe evaluacion?
		Optional<Evaluacion> evaluacion = evaluacionDAO.findById(dto.getIdEvaluacion());
		if (evaluacion.isEmpty()) throw new ServiceException("Evaluacion inexistente", 404);
		// Existe alumno?
		Optional<Alumno> alumno = alumnoRepository.findById(dto.getIdAlumno());
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
		Optional<Alumno> alumno = alumnoRepository.findById(alumnoId);
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		return calificacionRepository.findByEvaluacionAndAlumno(evaluacion.get(), alumno.get()).orElseGet(Calificacion::new);
	}

	public HistoriaAcademicaDTO getByCurso(String dni, UUID cursoId) throws ServiceException
	{
		Optional<Alumno> alumno = alumnoRepository.findAlumnoByPersona_Dni(dni);
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		List<Calificacion> calificaciones = calificacionRepository.findByAlumnoAndCurso(alumno.get().getId(), cursoId);

		HistoriaAcademicaDTO historiaAcademicaDTO = new HistoriaAcademicaDTO();
		historiaAcademicaDTO.setMaterias(
				calificaciones.stream()
						.collect(Collectors.groupingBy(c -> c.getEvaluacion().getMateria()))
						.entrySet().stream()
						.map(entry -> {
							HistoriaAcademicaMateriaDTO materiaDTO = new HistoriaAcademicaMateriaDTO();
							materiaDTO.setIdMateria(entry.getKey().getId());
							materiaDTO.setNombreMateria(entry.getKey().getNombre());
							materiaDTO.setCalificaciones(
									entry.getValue().stream()
											.map(calificacion -> {
												CalificacionHistoriaAcademicaDTO calificacionDTO = new CalificacionHistoriaAcademicaDTO();
												calificacionDTO.setIdEvaluacion(calificacion.getEvaluacion().getId());
												calificacionDTO.setNombreEvaluacion(calificacion.getEvaluacion().getDescripcion());
												calificacionDTO.setNota(calificacion.getNota());
												return calificacionDTO;
											})
											.collect(Collectors.toList())
							);
							return materiaDTO;
						})
						.collect(Collectors.toList())
		);
		return historiaAcademicaDTO;
	}
	public List<BoletinDTO> getBoletin(UUID cursoId) throws ServiceException
	{
		// Obtener todos los alumnos del curso
		List<Alumno> alumnos = alumnoRepository.findAll().stream()
				.filter(alumno -> alumno.getCurso() != null && alumno.getCurso().getId().equals(cursoId))
				.toList();

		// Generar boletines para cada alumno
		List<BoletinDTO> boletines = alumnos.stream()
				.map(alumno -> {
					BoletinDTO boletin = new BoletinDTO();
					boletin.setAlumno(alumnoMapper.map(alumno));
					try {
						boletin.setCalificaciones(getBoletin(alumno.getId(), cursoId));
					} catch (ServiceException e) {
						// won't happen
						System.err.println(e.getMessage());
					}
					return boletin;
				})
				.collect(Collectors.toList());
		return boletines;
	}

	public List<BoletinItemDTO> getBoletin(UUID alumnoId, UUID cursoId) throws ServiceException {
		Optional<Alumno> alumno = alumnoRepository.findById(alumnoId);
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
		List<Calificacion> calificaciones = calificacionRepository.findByAlumnoAndCurso(alumno.get().getId(), cursoId);

		return calificaciones.stream()
				.collect(Collectors.groupingBy(c -> c.getEvaluacion().getMateria().getNombre()))
				.entrySet().stream()
				.map(entry -> {
					BoletinItemDTO item = new BoletinItemDTO();
					item.setMateria(entry.getKey());

					List<Calificacion> calificacionesMateria = entry.getValue();
					item.setPrimerTrimestre(calcularPromedioTrimestre(calificacionesMateria, Month.MARCH, Month.MAY));
					item.setSegundoTrimestre(calcularPromedioTrimestre(calificacionesMateria, Month.JUNE, Month.AUGUST));
					item.setTercerTrimestre(calcularPromedioTrimestre(calificacionesMateria, Month.SEPTEMBER, Month.NOVEMBER));
					item.setFinal((item.getPrimerTrimestre() + item.getSegundoTrimestre() + item.getTercerTrimestre()) / 3);

					return item;
				})
				.collect(Collectors.toList());
	}
	private float calcularPromedioTrimestre(List<Calificacion> calificaciones, Month mesInicio, Month mesFin) {
		return (float) calificaciones.stream()
				.filter(c -> c.getEvaluacion().getFechaCreacion().getMonth().getValue() >= mesInicio.getValue()
						&& c.getEvaluacion().getFechaCreacion().getMonth().getValue() <= mesFin.getValue())
				.mapToDouble(Calificacion::getNota)
				.average()
				.orElse(0);
	}

	public HistoriaAcademicaDTO getByCurso(UUID alumnoId, UUID cursoId) throws ServiceException
	{
		Optional<Alumno> alumno = alumnoRepository.findById(alumnoId);
		if (alumno.isEmpty()) throw new ServiceException("Alumno no encontrado", 404);
		return getByCurso(alumno.get().getPersona().getDni(), cursoId);
	}
}
