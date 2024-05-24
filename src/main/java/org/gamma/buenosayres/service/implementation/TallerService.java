package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.TallerDAO;
import org.gamma.buenosayres.dto.TallerDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Taller;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TallerService {

	private final TallerDAO tallerDAO;
	private final AlumnoDAO alumnoDAO;

	public TallerService(TallerDAO tallerDAO, AlumnoDAO alumnoDAO)
	{
		this.tallerDAO = tallerDAO;
		this.alumnoDAO = alumnoDAO;
	}

	public List<Taller> getAll()
	{
		return tallerDAO.findAll();
	}

	public Taller newTaller(Taller tNuevo) throws ServiceException
	{
		for (Alumno alumno : tNuevo.getAlumnos()) {
			Alumno alumno1 = alumnoDAO.findById(alumno.getId()).orElseThrow(() -> new ServiceException(
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
			Alumno alumno1 = alumnoDAO.findById(id_alumno).orElseThrow(() -> new ServiceException(
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
		Alumno alumno = alumnoDAO.findById(id_alumno).orElseThrow(() -> new ServiceException(
				String.format("Alumno Inexistente: %s", id_alumno), 400
		));
		taller.getAlumnos().remove(alumno);
		alumno.getTalleres().remove(taller);
		tallerDAO.save(taller);
		alumnoDAO.save(alumno);
		return true;
	}
}
