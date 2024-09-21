package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoMapper;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.TipoConcepto;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.ConceptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/conceptos")
@CrossOrigin(origins = "*")
public class ConceptoController {
	private final ConceptoService service;
	private final ConceptoMapper mapper;
	@Autowired
	ConceptoController(ConceptoService service, ConceptoMapper mapper)
	{
		this.service = service;
		this.mapper = mapper;
	}
	@GetMapping
	ResponseEntity<?> get(@RequestParam(value = "type", required = false) TipoConcepto tipo,
						  @RequestParam(value = "nivel", required = false) Nivel nivel,
						  @RequestParam(value = "taller", required = false) UUID id_taller,
						  @RequestParam(value = "limit", defaultValue = "10") int limit)
	{
		try {
			return ResponseEntity.ok(service.get(tipo, nivel, id_taller, limit).stream().map(mapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping
	ResponseEntity<?> nuevoConcepto(@RequestBody ConceptoDTO concepto)
	{
		try {
			return ResponseEntity.ok(service.newConcepto(mapper.map(concepto)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
