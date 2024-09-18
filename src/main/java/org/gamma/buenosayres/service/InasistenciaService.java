package org.gamma.buenosayres.service;

import org.gamma.buenosayres.dto.AlumnoPerfectoDTO;
import org.gamma.buenosayres.dto.AlumnoTopDTO;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Inasistencia;
import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.InasistenciaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InasistenciaService {
	@Autowired
	private InasistenciaDAO inasistenciaDAO;
	@Autowired
	private AlumnoRepository alumnoRepository;

	public List<Inasistencia> getAll() throws ServiceException
	{
		return inasistenciaDAO.findAll();
	}

	public Inasistencia getById(UUID id) throws ServiceException
	{
		return inasistenciaDAO.findById(id)
				.orElseThrow(() -> new ServiceException("Inasistencia no encontrada", 404));
	}

	public Inasistencia create(Inasistencia inasistencia) throws ServiceException
	{
		Optional<Alumno> alumno = alumnoRepository.findById(inasistencia.getAlumno().getId());
		if (alumno.isEmpty()) {
			throw new ServiceException("Alumno no encontrado", 404);
		}
		inasistencia.setAlumno(alumno.get());
		return inasistenciaDAO.save(inasistencia);
	}

	public Inasistencia update(UUID id, Inasistencia inasistencia) throws ServiceException
	{
		Optional<Inasistencia> existingInasistencia = inasistenciaDAO.findById(id);
		if (existingInasistencia.isEmpty()) {
			throw new ServiceException("Inasistencia no encontrada", 404);
		}

		Inasistencia toUpdate = existingInasistencia.get();
		// Actualizar campos si es necesario
		if (inasistencia.getAlumno() != null && inasistencia.getAlumno().getId() != null) {
			Optional<Alumno> alumno = alumnoRepository.findById(inasistencia.getAlumno().getId());
			if (alumno.isEmpty()) {
				throw new ServiceException("Alumno no encontrado", 404);
			}
			toUpdate.setAlumno(alumno.get());
		}
		if (inasistencia.getFecha() != null) {
			toUpdate.setFecha(inasistencia.getFecha());
		}
		if (inasistencia.getCantidad() != 0) {
			toUpdate.setCantidad(inasistencia.getCantidad());
		}
		toUpdate.setJustificada(inasistencia.isJustificada());

		return inasistenciaDAO.save(toUpdate);
	}


	public List<AlumnoTopDTO> getTop5AlumnosConMasInasistencias() throws ServiceException {
		int currentYear = LocalDate.now().getYear();

		List<Inasistencia> inasistencias = inasistenciaDAO.findAll().stream()
				.filter(inasistencia -> inasistencia.getFecha().getYear() == currentYear)
				.toList();

		// Agrupar por alumno y contar fechas únicas
		Map<Alumno, Long> faltasPorAlumno = inasistencias.stream()
				.collect(Collectors.groupingBy(Inasistencia::getAlumno,
						Collectors.mapping(Inasistencia::getFecha, Collectors.counting())));

		List<AlumnoTopDTO> top5Alumnos = faltasPorAlumno.entrySet().stream()
				.map(entry -> new AlumnoTopDTO(entry.getKey().getCurso(), entry.getKey().getPersona().getNombre(), entry.getKey().getPersona().getApellido(), entry.getValue().intValue()))
				.sorted(Comparator.comparingInt(AlumnoTopDTO::getCantidadFaltas).reversed())
				.limit(5)
				.toList();

		return top5Alumnos;
	}

	public List<AlumnoPerfectoDTO> getAlumnosSinInasistencias() throws ServiceException
	{
		int currentYear = LocalDate.now().getYear();

		List<Inasistencia> inasistencias = inasistenciaDAO.findAll().stream()
				.filter(inasistencia -> inasistencia.getFecha().getYear() == currentYear)
				.toList();

		List<Alumno> alumnosConInasistencias = inasistencias.stream()
				.map(Inasistencia::getAlumno)
				.distinct()
				.toList();

		List<AlumnoPerfectoDTO> alumnosPerfectos = alumnoRepository.findAll().stream()
				.filter(alumno -> !alumnosConInasistencias.contains(alumno))
				.map(alumno -> new AlumnoPerfectoDTO(alumno.getCurso(), alumno.getPersona().getNombre(), alumno.getPersona().getApellido()))
				.toList();
		return alumnosPerfectos;
	}
}