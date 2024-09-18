package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.SancionDTO;
import org.gamma.buenosayres.mapper.SancionMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.SancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/sanciones")
@CrossOrigin(origins = "*")
public class SancionController {
	private SancionService sancionService;
	private SancionMapper sancionMapper;
	@Autowired
	public SancionController(SancionService sancionService, SancionMapper sancionMapper)
	{
		this.sancionService = sancionService;
		this.sancionMapper = sancionMapper;
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> get()
	{
		return ResponseEntity.ok(sancionService.get().stream().map(sancionMapper::map).toList());
	}
	@GetMapping("/{sancion}")
	public ResponseEntity<?> get(@PathVariable(value = "sancion") UUID sancion)
	{
		try {
			return ResponseEntity.ok(sancionMapper.map(sancionService.get(sancion)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping(produces = "application/json")
	public ResponseEntity<?> create(@RequestBody SancionDTO sancion)
	{
		try {
			return ResponseEntity.ok(sancionMapper.map(sancionService.create(sancion)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PutMapping("/{sancion}")
	public ResponseEntity<?> update(@PathVariable(value = "sancion") UUID sancionId, @RequestBody SancionDTO dto)
	{
		try {
			return ResponseEntity.ok(sancionMapper.map(sancionService.update(sancionId, dto)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}