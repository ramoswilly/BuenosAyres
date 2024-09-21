package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.ProfesorService;
import org.gamma.buenosayres.mapper.ProfesorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profesores")
@CrossOrigin(origins = "*")
public class ProfesorController {
	ProfesorService service;
	ProfesorMapper mapper;
	@Autowired
	public ProfesorController(ProfesorService service, ProfesorMapper mapper)
	{
		this.service = service;
		this.mapper = mapper;
	}
	@GetMapping("/{idProfesor}")
	public ResponseEntity<?> get(@PathVariable(value = "idProfesor") UUID idProfesor)
	{
		try {
			return ResponseEntity.ok(mapper.map(service.get(idProfesor)));
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> get(@RequestParam(value = "tipo", required = false) String rol,
						  @RequestParam(value = "nivel", required = false) Nivel nivel
						  /*,@RequestParam(value = "limit", defaultValue = "10") int limit*/)
	{
		return ResponseEntity.ok(service.get(rol, nivel).stream().map(mapper::map).toList());
	}
	@PostMapping(produces = "application/json")
	public ResponseEntity<?> create(@RequestBody ProfesorDTO dto)
	{
		try {
			return ResponseEntity.ok(mapper.map(service.create(mapper.map(dto))));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/rendimiento")
	public ResponseEntity<?> getRendimientoPorProfesor() {
		try {
			return ResponseEntity.ok(service.getRendimientoPorProfesor());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PutMapping("/{profesorId}")
	public ResponseEntity<?> update(@PathVariable(value = "profesorId") UUID profesorId, @RequestBody ProfesorDTO dto)
	{
		try {
			dto.setId(profesorId); // Asegurar que el DTO tenga el ID correcto
			return ResponseEntity.ok(mapper.map(service.update(dto)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
