package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.CalificacionDTO;
import org.gamma.buenosayres.mapper.CalificacionMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/calificaciones")
public class CalificacionController {
	@Autowired
	private CalificacionMapper calificacionMapper;
	@Autowired
	private CalificacionService calificacionService;
	@PutMapping
	public ResponseEntity<?> update(@RequestBody CalificacionDTO calificacionDTO)
	{
		try {
			return ResponseEntity.ok(calificacionMapper.map(calificacionService.update(calificacionDTO)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/{evaluacionId}/{alumnoId}")
	public ResponseEntity<?> get(@PathVariable(value = "evaluacionId") UUID evaluacionId, @PathVariable(value = "alumnoId") UUID alumnoId)
	{
		try {
			return ResponseEntity.ok(calificacionMapper.map(calificacionService.get(evaluacionId, alumnoId)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/{cursoId}")
	public ResponseEntity<?> getByCurso(Authentication authentication,
										@PathVariable(value = "cursoId") UUID cursoId,
										@RequestParam(value = "alumno", required = false) UUID alumnoId)
	{
		try {
			if (alumnoId != null) {
				return ResponseEntity.ok(calificacionService.getByCurso(alumnoId, cursoId));
			}
			return ResponseEntity.ok(calificacionService.getByCurso(authentication.getName(), cursoId));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/boletin")
	public ResponseEntity<?> getBoletin(@RequestParam(value = "alumno", required = false) UUID alumnoId,
										@RequestParam(value = "curso", required = true) UUID cursoId) {
		try {
			if (alumnoId == null)
				return ResponseEntity.ok(calificacionService.getBoletin(cursoId));
			return ResponseEntity.ok(calificacionService.getBoletin(alumnoId, cursoId));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
