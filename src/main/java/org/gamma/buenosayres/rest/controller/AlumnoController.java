package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.mapper.AlumnoMapper;
import org.gamma.buenosayres.dto.ActualizarAlumnoDTO;
import org.gamma.buenosayres.dto.CrearAlumnoDTO;
import org.gamma.buenosayres.dto.ListarAlumnoDTO;
import org.gamma.buenosayres.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.gamma.buenosayres.service.implementation.AlumnoService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {
	private final AlumnoService service;
	private final AlumnoMapper alumnoMapper;
	@Autowired
	public AlumnoController(AlumnoService service, AlumnoMapper alumnoMapper) {
		this.service = service;
		this.alumnoMapper = alumnoMapper;
	}
	@GetMapping(produces = "application/json")
	public List<ListarAlumnoDTO> getAllAlumnos()
	{
		return service.findAll().stream().map(alumnoMapper::map).toList();
	}
	@PostMapping
	public ResponseEntity<String> newAlumno(@RequestBody CrearAlumnoDTO alumno)
	{
		try {
			service.newAlumno(alumnoMapper.map(alumno));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Alumno registrado");
	}
	@PutMapping
	public ResponseEntity<String> updateAlumno(@RequestBody ActualizarAlumnoDTO alumno)
	{
		try {
			service.updateAlumno(alumnoMapper.map(alumno));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Alumno actualizado");
	}
}
