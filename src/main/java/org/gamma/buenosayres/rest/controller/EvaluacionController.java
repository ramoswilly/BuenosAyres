package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.dto.EvaluacionDTO;
import org.gamma.buenosayres.mapper.EvaluacionMapper;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/evaluaciones")
@CrossOrigin(origins = "*")
public class EvaluacionController {
	private EvaluacionService evaluacionService;
	private EvaluacionMapper evaluacionMapper;
	@Autowired
	public EvaluacionController(EvaluacionService evaluacionService, EvaluacionMapper evaluacionMapper)
	{
		this.evaluacionService = evaluacionService;
		this.evaluacionMapper = evaluacionMapper;
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> get(@RequestParam(value = "materia") UUID idMateria)
	{
		try {
			return ResponseEntity.ok(evaluacionService.get(idMateria)
					.stream().map(evaluacionMapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping
	public ResponseEntity<?> create(Authentication authentication, @RequestBody EvaluacionDTO evaluacionDTO)
	{
		try {
			return ResponseEntity.ok(evaluacionMapper.map(evaluacionService.create(authentication.getName(), evaluacionDTO)));
			// return ResponseEntity.ok(evaluacionMapper.map(evaluacionService.create(evaluacionDTO)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
