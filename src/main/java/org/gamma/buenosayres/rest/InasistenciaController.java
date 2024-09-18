package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.InasistenciaDTO;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.mapper.InasistenciaMapper;
import org.gamma.buenosayres.service.InasistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inasistencias")
@CrossOrigin(origins = "*")
public class InasistenciaController {

	@Autowired
	private InasistenciaService inasistenciaService;

	@Autowired
	private InasistenciaMapper inasistenciaMapper;

	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			return ResponseEntity.ok(inasistenciaService.getAll().stream().map(inasistenciaMapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
		try {
			return ResponseEntity.ok(inasistenciaMapper.map(inasistenciaService.getById(id)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody InasistenciaDTO dto) {
		try {
			return ResponseEntity.ok(inasistenciaMapper.map(inasistenciaService.create(inasistenciaMapper.map(dto))));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody InasistenciaDTO dto) {
		try {
			return ResponseEntity.ok(inasistenciaMapper.map(inasistenciaService.update(id, inasistenciaMapper.map(dto))));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}