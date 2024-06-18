package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.CursoDAO;
import org.gamma.buenosayres.model.TipoPersona;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlumnoService {
	private final AlumnoDAO alumnoDAO;
	private final CursoDAO cursoDAO;

	@Autowired
	public AlumnoService(AlumnoDAO alumnoDAO, CursoDAO cursoDAO)
	{
		this.alumnoDAO = alumnoDAO;
		this.cursoDAO = cursoDAO;
	}

	public void newAlumno(Alumno alumno) throws ServiceException
	{
		// Revisar que no exista el alumno
		if (alumnoDAO.findAlumnoByPersona_Dni(alumno.getPersona().getDni()).isPresent()) throw new ServiceException("Alumno ya registrado\n", 400);

		// Revisar que exista curso
		Curso curso = cursoDAO.findById(alumno.getCurso().getId()).orElseThrow(
				()->new ServiceException("Curso inexistente\n", 400)
		);
		alumno.getPersona().setTipo(TipoPersona.ALUMNO);
		alumnoDAO.save(alumno);
	}

	public List<Alumno> findAll()
	{
		return alumnoDAO.findAll();
	}

	public void updateAlumno(Alumno updatedAlumno) throws ServiceException {
		Optional<Alumno> alumnoOptional = alumnoDAO.findById(updatedAlumno.getId());
		if (alumnoOptional.isEmpty()) {
			throw new ServiceException("Alumno inexistente\n", 400);
		}

		Alumno existingAlumno = alumnoOptional.get();

		if (existingAlumno.getPersona() != null) {
			Optional<Alumno> alumnoByPersonaDni = alumnoDAO.findAlumnoByPersona_Dni(updatedAlumno.getPersona().getDni());
			if (alumnoByPersonaDni.isPresent() && alumnoByPersonaDni.get().getId() != updatedAlumno.getId())
				throw new ServiceException("No se puede actualizar el DNI, corresponde a otro alumno\n", 400);
		}

		if (updatedAlumno.getCurso() != null && updatedAlumno.getCurso().getId() != null) {
			Curso curso = cursoDAO.findById(updatedAlumno.getCurso().getId())
					.orElseThrow(() -> new ServiceException("Curso inexistente\n", 400));
			existingAlumno.setCurso(curso);
		}

		if (updatedAlumno.getPersona() != null) {
			Persona updatedPersona = updatedAlumno.getPersona();
			if (updatedPersona.getDni() != null) {
				existingAlumno.getPersona().setDni(updatedPersona.getDni());
			}
			if (updatedPersona.getNombre() != null) {
				existingAlumno.getPersona().setNombre(updatedPersona.getNombre());
			}
			if (updatedPersona.getApellido() != null) {
				existingAlumno.getPersona().setApellido(updatedPersona.getApellido());
			}
			if (updatedPersona.getDireccion() != null) {
				existingAlumno.getPersona().setDireccion(updatedPersona.getDireccion());
			}
		}

		// Save the updated Alumno
		alumnoDAO.save(existingAlumno);
	}

	public Alumno getAlumno(UUID idAlumno) throws ServiceException
	{
		return alumnoDAO.findById(idAlumno).orElseThrow(() -> new ServiceException("No encontrado", 404));
	}
}
