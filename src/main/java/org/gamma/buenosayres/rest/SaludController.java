package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.SaludDTO;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.mapper.SaludMapper;
import org.gamma.buenosayres.service.SaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/salud")
@CrossOrigin(origins = "*")
public class SaludController {

	@Autowired
	private SaludService saludService;

	@Autowired
	private SaludMapper saludMapper;

	@GetMapping
	public ResponseEntity<?> getSalud(Authentication authentication) {
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_PRECEPTOR"))) {
			return ResponseEntity.ok(saludService.obtenerSaludPorPreceptor(authentication.getName()).stream().map(saludMapper::map).toList());
		}
		return ResponseEntity.ok(saludService.obtenerSalud().stream().map(saludMapper::map).toList());
	}
	@GetMapping("/{idPersona}")
	public ResponseEntity<?> getSaludPorId(@PathVariable("idPersona") UUID idPersona) {
		try {
			return ResponseEntity.ok(saludMapper.map(saludService.obtenerSaludPorId(idPersona)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}

	@PutMapping("/{idPersona}")
	public ResponseEntity<?> actualizarSalud(@PathVariable("idPersona") UUID idPersona, @RequestBody SaludDTO saludDTO) {
		try {
			return ResponseEntity.ok(saludMapper.map(saludService.actualizarSalud(idPersona, saludDTO)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}