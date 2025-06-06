package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.EvaluacionDTO;
import org.gamma.buenosayres.mapper.EvaluacionMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.service.EvaluacionService;
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
	public ResponseEntity<?> get(Authentication authentication, @RequestParam(value = "materia") UUID idMateria)
	{
		try {
			// Obtener solo evaluaciones entregables // TP // No vencidas
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ALUMNO"))) {
				return ResponseEntity.ok(evaluacionService.getEntregables(idMateria)
						.stream()
						.filter(Evaluacion::isHabilitada)
						.map(evaluacionMapper::map).toList());
			}
			return ResponseEntity.ok(evaluacionService.get(idMateria)
					.stream().map(evaluacionMapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") UUID id) {
		try {
			return ResponseEntity.ok(evaluacionMapper.map(evaluacionService.getById(id)));
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
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") UUID id, @RequestBody EvaluacionDTO dto) {
		try {
			// Actualizar la evaluacion y obtener solo los cambios
			return ResponseEntity.ok(evaluacionMapper.map(evaluacionService.update(id, dto)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
