package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.MateriaDTO;
import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.mapper.MateriaMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/materias")
@CrossOrigin(origins = "*")
public class MateriaController {
	private MateriaService materiaService;
	private MateriaMapper materiaMapper;
	@Autowired
	public MateriaController(MateriaService materiaService, MateriaMapper materiaMapper)
	{
		this.materiaService = materiaService;
		this.materiaMapper = materiaMapper;
	}
	@PutMapping("/{materia}/profesor")
	public ResponseEntity<?> asignarProfesor(@PathVariable(value = "materia") UUID idMateria,
											 @RequestBody ProfesorDTO profesor)
	{
		try {
			return ResponseEntity.ok(materiaMapper.map(materiaService.asignarProfesor(idMateria, profesor)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> get(Authentication authentication)
	{
		// Obtener materias del profe
		if (authentication.getAuthorities().stream().anyMatch(auth -> Objects.equals(auth.getAuthority(), "ROLE_PROFESOR"))) {
			return ResponseEntity.ok(materiaService.get(authentication).stream().map(materiaMapper::map).toList());
		}
		// Devolver todo
		return ResponseEntity.ok(materiaService.get().stream().map(materiaMapper::map).toList());
	}
	@GetMapping("/{materia}")
	public ResponseEntity<?> get(@PathVariable(value = "materia") UUID materia)
	{
		try {
			return ResponseEntity.ok(materiaMapper.map(materiaService.get(materia)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping(produces = "application/json")
	public ResponseEntity<?> create(@RequestBody MateriaDTO materia)
	{
		try {
			return ResponseEntity.ok(materiaMapper.map(materiaService.create(materia)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PutMapping(produces = "application/json")
	public ResponseEntity<?> update(@RequestBody MateriaDTO materia)
	{
		try {
			return ResponseEntity.ok(materiaMapper.map(materiaService.update(materia)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/rendimiento")
	public ResponseEntity<?> getRendimientoPorMateria() {
		try {
			return ResponseEntity.ok(materiaService.getRendimientoPorMateria());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
