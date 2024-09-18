package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.EntregaDTO;
import org.gamma.buenosayres.mapper.EntregaMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/entregas")
@CrossOrigin(origins = "*")
public class EntregaController {
	private EntregaService entregaService;
	private EntregaMapper entregaMapper;

	@Autowired
	public EntregaController(EntregaService entregaService, EntregaMapper entregaMapper) {
		this.entregaService = entregaService;
		this.entregaMapper = entregaMapper;
	}
	@PostMapping
	public ResponseEntity<?> create(@RequestBody EntregaDTO entregaDTO) {
		try {
			return ResponseEntity.ok(entregaMapper.map(entregaService.create(entregaDTO)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getByEvaluacion(@RequestParam(value = "evaluacion") UUID idEvaluacion) {
		try {
			return ResponseEntity.ok(entregaService.getByEvaluacion(idEvaluacion)
					.stream().map(entregaMapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}