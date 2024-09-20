package org.gamma.buenosayres.service;

import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.*;
import org.gamma.buenosayres.dto.*;
import org.gamma.buenosayres.mapper.AlumnoMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalificacionService {
	@Autowired
	private EvaluacionDAO evaluacionDAO;
	@Autowired
	private EntregaDAO entregaDAO;
	@Autowired
	private AlumnoRepository alumnoRepository;
	@Autowired
	private AlumnoMapper alumnoMapper;
	@Autowired
	private CursoDAO cursoDAO;
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
	@Scheduled(cron = "0 0 0 * * ?") // Se ejecuta todos los días a las 00:00
	public void DesaprobarVencidos()
	{
		//Obtener vencidos
		List<Evaluacion> vencidas = evaluacionDAO.findAllByFechaVencimientoAfter(LocalDate.now());

		for (Evaluacion vencida : vencidas) {
			vencida.getMateria().getCurso().getAlumnos()
					.stream()
					.filter(alumno -> entregaDAO.findByAlumnoAndEvaluacion(alumno, vencida).isPresent())
					// No entregó 1 (uno)
					.forEach(
							alumno -> {
								Calificacion calificacion = new Calificacion();
								calificacion.setAlumno(alumno);
								calificacion.setNota(1);
								calificacion.setEvaluacion(vencida);
								calificacionRepository.save(calificacion);
							}
					);
		}
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
	private Optional<MejorPromedioDTO> obtenerMejorPromedioCurso(Curso curso) {
		// Obtener las calificaciones del año actual para el curso
		List<Calificacion> calificacionesCurso = calificacionRepository.findByCursoAnual(curso.getId(), LocalDate.now().getYear());

		// Agrupar las calificaciones por alumno y calcular el promedio
		Map<Alumno, Double> promediosAlumnos = calificacionesCurso.stream()
				.collect(Collectors.groupingBy(Calificacion::getAlumno, Collectors.averagingDouble(Calificacion::getNota)));

		// Encontrar el alumno con el mejor promedio
		Optional<Map.Entry<Alumno, Double>> mejorPromedio = promediosAlumnos.entrySet().stream()
				.max(Comparator.comparingDouble(Map.Entry::getValue));

		// Crear el DTO con la información del mejor promedio
		return mejorPromedio.map(entry -> new MejorPromedioDTO(curso, entry.getKey().getPersona().getNombre(), entry.getKey().getPersona().getApellido(), entry.getValue()));
	}
	public List<MejorPromedioDTO> obtenerMejoresPromediosPorCurso() throws ServiceException {
		// Obtener todos los cursos de primaria y secundaria
		List<Curso> cursos = cursoDAO.findByNiveles(List.of(Nivel.PRIMARIA, Nivel.SECUNDARIA));

		// Obtener los mejores promedios por curso
		return cursos.stream()
				.map(this::obtenerMejorPromedioCurso)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}
}
