package org.gamma.buenosayres.service;

import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.repository.AlumnoRepository;
import org.gamma.buenosayres.repository.InasistenciaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InasistenciaService {
	@Autowired
	private InasistenciaDAO inasistenciaDAO;
	@Autowired
	private AlumnoRepository alumnoRepository;

	public List<Inasistencia> getAll() throws ServiceException {
		return inasistenciaDAO.findAll();
	}
	public Inasistencia getById(UUID id) throws ServiceException {
		return inasistenciaDAO.findById(id)
				.orElseThrow(() -> new ServiceException("Inasistencia no encontrada", 404));
	}

	public Inasistencia create(Inasistencia inasistencia) throws ServiceException {
		Optional<Alumno> alumno = alumnoRepository.findById(inasistencia.getAlumno().getId());
		if (alumno.isEmpty()) {
			throw new ServiceException("Alumno no encontrado", 404);
		}
		inasistencia.setAlumno(alumno.get());
		return inasistenciaDAO.save(inasistencia);
	}

	public Inasistencia update(UUID id, Inasistencia inasistencia) throws ServiceException {
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
}