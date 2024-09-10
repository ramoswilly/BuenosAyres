package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.dto.CalificacionDTO;
import org.gamma.buenosayres.mapper.CalificacionMapper;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
