package org.gamma.buenosayres.service;

import org.gamma.buenosayres.dto.AlumnoSancionDTO;
import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.SancionDAO;
import org.gamma.buenosayres.dto.SancionDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Sancion;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SancionService {
	private SancionDAO sancionDAO;
	private AlumnoRepository alumnoRepository;


	@Autowired
	public SancionService(SancionDAO sancionDAO, AlumnoRepository alumnoRepository)
	{
		this.sancionDAO = sancionDAO;
		this.alumnoRepository = alumnoRepository;
	}

	public List<Sancion> get()
	{
		return sancionDAO.findAll();
	}

	public Sancion get(UUID sancion) throws ServiceException
	{
		return sancionDAO.findById(sancion)
				.orElseThrow(() -> new ServiceException("Sanción inexistente", 404));
	}

	public Sancion create(SancionDTO dto) throws ServiceException
	{
		Optional<Alumno> alumno = alumnoRepository.findById(dto.getAlumnoId());
		if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 400);
		Sancion sancion = new Sancion();
		sancion.setAlumno(alumno.get());
		sancion.setCausa(dto.getCausa());
		sancion.setFecha(dto.getFecha());
		sancion.setGravedad(dto.getGravedad());
		sancion.setHabilitada(true);
		return sancionDAO.save(sancion);
	}

	public Sancion update(UUID sancionId, SancionDTO dto) throws ServiceException
	{
		Optional<Sancion> sancion = sancionDAO.findById(sancionId);
		if (sancion.isEmpty()) throw new ServiceException("Sanción inexistente", 404);
		// Actualizar alumno
		if (dto.getAlumnoId() != null) {
			Optional<Alumno> alumno = alumnoRepository.findById(dto.getAlumnoId());
			if (alumno.isEmpty()) throw new ServiceException("Alumno inexistente", 404);
			sancion.get().setAlumno(alumno.get());
		}
		// Actualizar fecha
		if (dto.getFecha() != null) {
			sancion.get().setFecha(dto.getFecha());
		}
		// Actualizar causa
		if (dto.getCausa() != null) {
			sancion.get().setCausa(dto.getCausa());
		}
		// Actualizar gravedad
		if (dto.getGravedad() != null) {
			sancion.get().setGravedad(dto.getGravedad());
		}
		// Actualizar habilitada
		sancion.get().setHabilitada(dto.isHabilitada());
		// Guardar
		return sancionDAO.save(sancion.get());
	}
	public List<AlumnoSancionDTO> getTop10AlumnosConMasSanciones() throws ServiceException {
		int currentYear = LocalDate.now().getYear();

		List<Sancion> sanciones = sancionDAO.findAll().stream()
				.filter(sancion -> sancion.getFecha().getYear() == currentYear)
				.toList();

		Map<Alumno, Long> sancionesPorAlumno = sanciones.stream()
				.collect(Collectors.groupingBy(Sancion::getAlumno, Collectors.counting()));

		return sancionesPorAlumno.entrySet().stream()
				.map(entry -> new AlumnoSancionDTO(
						entry.getKey().getCurso(),
						entry.getKey().getPersona().getNombre(),
						entry.getKey().getPersona().getApellido(),
						entry.getValue()))
				.sorted(Comparator.comparingLong(AlumnoSancionDTO::getCantidadSanciones).reversed())
				.limit(10)
				.toList();
	}
}