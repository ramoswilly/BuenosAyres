package org.gamma.buenosayres.rest.controller;

import java.util.Date;

import org.gamma.buenosayres.mapper.FacturacionMapper;
import org.gamma.buenosayres.service.implementation.FacturacionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facturacion")
@CrossOrigin(origins = "*")
public class FacturacionController {

	private final FacturacionService facturacionService;
	private final FacturacionMapper facturacionMapper;

	public FacturacionController(FacturacionService facturacionService, FacturacionMapper facturacionMapper)
	{
		this.facturacionService = facturacionService;
		this.facturacionMapper = facturacionMapper;
	}
	@GetMapping
	public ResponseEntity<?> obtenerFacturas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date periodo)
	{
		return ResponseEntity.ok(facturacionService.obtenerFacturas(periodo).stream().map(facturacionMapper::map).toList());
	}
	@PostMapping
	public ResponseEntity<?> facturar(@RequestParam(required = false, defaultValue = "false") boolean adicionales,
									  @RequestParam(required = false,  defaultValue = "false") boolean matriculas)
	{
		facturacionService.facturar(adicionales, matriculas);
		return ResponseEntity.accepted().body("");
	}

	@GetMapping("/periodos")
	public ResponseEntity<?> obtenerPeriodos()
	{
		return ResponseEntity.ok(facturacionService.obtenerPeriodos());
	}
}
