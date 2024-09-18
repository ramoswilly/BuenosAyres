package org.gamma.buenosayres.rest;


import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.mapper.CursoMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cursos")
@CrossOrigin(origins = "*")
public class CursoController {
	private final CursoService service;
	private final CursoMapper mapper;
	@Autowired
	public CursoController(CursoService service, CursoMapper mapper)
	{
		this.service = service;
		this.mapper = mapper;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getAllCursos(Authentication authentication, @RequestParam(value = "alumno", required = false) UUID alumnoId)
	{
		// Obtener cursos del alumno (por id)
		if (alumnoId != null) {
			try {
				return ResponseEntity.ok(service.findByAlumno(alumnoId).stream().map(mapper::map));
			} catch (ServiceException e) {
				return ResponseEntity.status(e.getCode()).body(e.getMessage());
			}
		}

		// Obtener cursos del alumno (por authority)
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ALUMNO"))) {
			try {
				return ResponseEntity.ok(service.findByAlumno(authentication.getName()).stream().map(mapper::map));
			} catch (ServiceException e) {
				return ResponseEntity.status(e.getCode()).body(e.getMessage());
			}
		}
		// Obtener cursos del director
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_DIRECTOR"))) {
			try {
				return ResponseEntity.ok(service.findByDirector(authentication.getName()).stream().map(mapper::map));
			} catch (ServiceException e) {
				return ResponseEntity.status(e.getCode()).body(e.getMessage());
			}
		}
		// Generico, todos
		return ResponseEntity.ok(service.findAll().stream().map(mapper::map));
	}

	@PutMapping("/{curso}/responsable")
	public ResponseEntity<?> asignarProfesor(@PathVariable(value = "curso") UUID curso, @RequestBody ProfesorDTO profesor) throws ServiceException
	{
		try {
			return ResponseEntity.ok(mapper.map(service.asignarProfesor(curso, profesor)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@GetMapping("/rendimiento")
	public ResponseEntity<?> getRendimientoPorCurso() {
		try {
			return ResponseEntity.ok(service.getRendimientoPorCurso());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/problematicos")
	public ResponseEntity<?> getCursosProblematicos() {
		try {
			return ResponseEntity.ok(service.getCursosProblematicos());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
