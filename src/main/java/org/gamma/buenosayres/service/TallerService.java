package org.gamma.buenosayres.service;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.repository.AlumnoRepository;
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

	public TallerService(TallerDAO tallerDAO, AlumnoRepository alumnoRepository)
	{
		this.tallerDAO = tallerDAO;
		this.alumnoRepository = alumnoRepository;
	}

	public List<Taller> getAll()
	{
		return tallerDAO.findAll();
	}

	public Taller newTaller(Taller tNuevo) throws ServiceException
	{
		for (Alumno alumno : tNuevo.getAlumnos()) {
			Alumno alumno1 = alumnoRepository.findById(alumno.getId()).orElseThrow(() -> new ServiceException(
					String.format("Alumno Inexistente: %s", alumno.getId()), 400
			));
			alumno1.getTalleres().add(tNuevo);
		}
		return tallerDAO.save(tNuevo);
	}

	public List<Alumno> getAlumnosInTaller(UUID id_taller) throws ServiceException
	{
		Taller taller = tallerDAO.findById(id_taller).orElseThrow(() -> new ServiceException("Taller inexistente", 400));
		return taller.getAlumnos();
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
