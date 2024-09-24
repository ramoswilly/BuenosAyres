package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dto.CrearTallerDTO;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.ProfesorDAO;
import org.gamma.buenosayres.repository.TallerDAO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Taller;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TallerService {

	private final TallerDAO tallerDAO;
	private final AlumnoRepository alumnoRepository;
	private final ProfesorDAO profesorDAO;
	public TallerService(TallerDAO tallerDAO, AlumnoRepository alumnoRepository, ProfesorDAO profesorDAO)
	{
		this.tallerDAO = tallerDAO;
		this.alumnoRepository = alumnoRepository;
		this.profesorDAO = profesorDAO;
	}

	public List<Taller> getAll()
	{
		return tallerDAO.findAll();
	}

	public Taller newTaller(CrearTallerDTO tNuevo) throws ServiceException
	{
		Profesor profesor = profesorDAO.findById(tNuevo.getProfesorId()).orElseThrow(() -> new ServiceException("Profesor no encontrado", 404));
		Taller taller = new Taller();
		taller.setId(null);
		taller.setDescripcion(tNuevo.getNombre());
		taller.setNivel(tNuevo.getNivel());
		taller.setProfesor(profesor);
		return tallerDAO.save(taller);
	}
	public CrearTallerDTO getTallerById(UUID idTaller) throws ServiceException {
		Taller taller = tallerDAO.findById(idTaller)
				.orElseThrow(() -> new ServiceException("Taller no encontrado", 404));
		CrearTallerDTO tallerDTO = new CrearTallerDTO();
		tallerDTO.setNombre(taller.getDescripcion());
		tallerDTO.setNivel(taller.getNivel());
		tallerDTO.setProfesorId(taller.getProfesor() != null ? taller.getProfesor().getId() : null);
		tallerDTO.setProfesor(taller.getProfesor() != null ? taller.getProfesor().getPersona().getNombre() + " " + taller.getProfesor().getPersona().getApellido() : null);
		return tallerDTO;
	}
	@Transactional
	public CrearTallerDTO updateTaller(UUID idTaller, CrearTallerDTO tallerDTO) throws ServiceException {
		Taller taller = tallerDAO.findById(idTaller)
				.orElseThrow(() -> new ServiceException("Taller no encontrado", 404));
		if (tallerDTO.getProfesorId() != null) {
			Profesor profesor = profesorDAO.findById(tallerDTO.getProfesorId()).orElseThrow(() -> new ServiceException("Profesor no encontrado", 404));
			taller.setProfesor(profesor);
		}
		if (tallerDTO.getNombre() != null) {
			taller.setDescripcion(tallerDTO.getNombre());
		}
		if (tallerDTO.getNivel() != null) {
			taller.setNivel(tallerDTO.getNivel());
		}
		tallerDAO.save(taller);
		return tallerDTO;
	}
	public List<Alumno> getAlumnosInTaller(UUID id_taller) throws ServiceException
	{
		Taller taller = tallerDAO.findById(id_taller).orElseThrow(() -> new ServiceException("Taller inexistente", 400));
		return taller.getAlumnos().stream().filter(Alumno::isHabilitado).toList();
	}
	@Transactional
	public Taller altaAlumnos(UUID id_taller, List<UUID> alumnos) throws ServiceException
	{
		Taller taller = tallerDAO.findById(id_taller).orElseThrow(() -> new ServiceException("Taller inexistente", 400));

		for (UUID id_alumno : alumnos) {
			Alumno alumno1 = alumnoRepository.findById(id_alumno).orElseThrow(() -> new ServiceException(
					String.format("Alumno Inexistente: %s", id_alumno), 400
			));

			alumno1.getTalleres().add(taller);
			taller.getAlumnos().add(alumno1);
		}
		System.out.println(taller);
		for (Alumno a : taller.getAlumnos()) {
			System.out.println(a.getTalleres());
		}
		tallerDAO.save(taller);
		return taller;
	}
	@Transactional
	public boolean removerAlumno(UUID idTaller, UUID id_alumno) throws ServiceException
	{
		Taller taller = tallerDAO.findById(idTaller).orElseThrow(() -> new ServiceException("Taller inexistente", 400));
		Alumno alumno = alumnoRepository.findById(id_alumno).orElseThrow(() -> new ServiceException(
				String.format("Alumno Inexistente: %s", id_alumno), 400
		));
		taller.getAlumnos().remove(alumno);
		alumno.getTalleres().remove(taller);
		tallerDAO.save(taller);
		alumnoRepository.save(alumno);
		return true;
	}
}
