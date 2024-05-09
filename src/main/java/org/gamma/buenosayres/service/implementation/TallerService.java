package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.TallerDAO;
import org.gamma.buenosayres.dto.TallerDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Taller;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

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
}
