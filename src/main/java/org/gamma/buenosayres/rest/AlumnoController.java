package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.mapper.AlumnoMapper;
import org.gamma.buenosayres.dto.AlumnoDTO;

import org.gamma.buenosayres.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.gamma.buenosayres.service.AlumnoService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {
	private final AlumnoService service;
	private final AlumnoMapper alumnoMapper;
	@Autowired
	public AlumnoController(AlumnoService service, AlumnoMapper alumnoMapper)
	{
		this.service = service;
		this.alumnoMapper = alumnoMapper;
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getAllAlumnos(Authentication authentication, @RequestParam(value = "curso", required = false) UUID cursoId)
	{
		try {
			return ResponseEntity.ok(service.findAll(authentication, cursoId).stream().map(alumnoMapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping
	@RequestMapping("/{id}")
	public ResponseEntity<?> getAlumno(@PathVariable(value = "id") UUID idAlumno)
	{
		try {
			return ResponseEntity.ok(alumnoMapper.map(service.getAlumno(idAlumno)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping
	public ResponseEntity<?> newAlumno(@RequestBody AlumnoDTO alumno)
	{
		try {
			return ResponseEntity.ok(alumnoMapper.map(service.newAlumno(alumnoMapper.map(alumno))));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateAlumno(@PathVariable(value = "id") UUID idAlumno, @RequestBody AlumnoDTO alumnoDTO)
	{
		try {
			alumnoDTO.setId(idAlumno);
			service.updateAlumno(alumnoMapper.map(alumnoDTO));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Alumno actualizado");
	}
}
