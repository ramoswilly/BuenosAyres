package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.ProfesorService;
import org.gamma.buenosayres.mapper.ProfesorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	/*@GetMapping(produces = "application/json")
	@Deprecated
	public List<ProfesorDTO> get()
	{
		return service.get().stream().map(mapper::map).toList();
	}*/
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
}
