package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.mapper.FamiliaMapper;
import org.gamma.buenosayres.dto.ListarFamiliaDTO;
import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/familias")
public class FamiliaController {
	private final FamiliaService familiaService;
	private final FamiliaMapper familiaMapper;

	@Autowired
	public FamiliaController(FamiliaService familiaService, FamiliaMapper familiaMapper)
	{
		this.familiaService = familiaService;
		this.familiaMapper= familiaMapper;
	}

	@GetMapping(produces = "application/json")
	public List<ListarFamiliaDTO> getAllFamilias()
	{
		List<Familia> familias = familiaService.getAll();

		return familiaMapper.map(familiaService.getAll());
	}
	@PostMapping
	public ResponseEntity<String> newFamilia(@RequestBody Map<String, String> apellido)
	{
		familiaService.newFamilia(apellido.get("apellido")); // No olvidar valir que no sea NULL, TODO: Validación
		return ResponseEntity.ok("Familia creada");
	}
	@PostMapping("/{familia}")
	public ResponseEntity<String> addPersona(@PathVariable UUID familia, @RequestBody Map<String, UUID> persona)
	{
		try {
			familiaService.addPersona(familia, persona.get("persona"));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.ok("Persona Añadida");
	}
}
