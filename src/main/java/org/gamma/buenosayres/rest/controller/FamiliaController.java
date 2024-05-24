package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.dto.ActualizarFamiliaDTO;
import org.gamma.buenosayres.dto.FamiliaDTO;
import org.gamma.buenosayres.dto.MiembrosFamiliaDTO;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/familias")
@CrossOrigin(origins="*")
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
	public List<FamiliaDTO> getAllFamilias()
	{
		List<Familia> familias = familiaService.getAll();
		return familiaMapper.map(familiaService.getAll());
	}
	@GetMapping("/{familia}")
	public ResponseEntity<?> detalleFamilia(@PathVariable(value = "familia") UUID id_familia)
	{
		Optional<Familia> familia = familiaService.find(id_familia);
		if (familia.isPresent()) return ResponseEntity.ok(familiaMapper.map(familia.get()));
		return ResponseEntity.notFound().build();
	}
	@PostMapping
	public ResponseEntity<String> newFamilia(@RequestBody Map<String, String> apellido)
	{
		familiaService.newFamilia(apellido.get("apellido")); // No olvidar valir que no sea NULL, TODO: Validación
		return ResponseEntity.ok("Familia creada");
	}
	@PostMapping("/{familia}")
	@Deprecated
	public ResponseEntity<String> addPersona(@PathVariable UUID familia, @RequestBody Map<String, UUID> persona)
	{
		try {
			familiaService.addPersona(familia, persona.get("persona"));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.ok("Persona Añadida");
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> actualizarFamilia(@PathVariable UUID id, @RequestBody FamiliaDTO familia)
	{
		try {
			familia.setId(id);
			familiaService.actualizarFamilia(familiaMapper.map(familia));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.ok("Familia Actualizada");
	}
	@PostMapping("/{familia}/miembros")
	public ResponseEntity<String> agregarMiembros(@PathVariable UUID familia, @RequestBody Map<String, List<UUID>> miembros)
	{
		try {
			familiaService.agregarMiembros(familiaMapper.map(familia, miembros));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.ok("Miembros agregados");
	}
	@DeleteMapping("/{familia}/miembros")
	public ResponseEntity<String> removerMiembro(@PathVariable UUID familia, @RequestBody Map<String, UUID> persona)
	{
		try {
			familiaService.removerMiembro(familia, persona.get("persona"));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.ok("Miembro removido");
	}
	@GetMapping("/{familia}/miembros")
	public ResponseEntity<?> verMiembros(@PathVariable UUID familia)
	{
		try {
			List<MiembrosFamiliaDTO> miembros = familiaMapper.mapTo(familiaService.getMiembrosOf(familia));
			return ResponseEntity.ok(miembros);
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}