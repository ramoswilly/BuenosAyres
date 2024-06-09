package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.service.implementation.FacturacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facturacion")
@CrossOrigin(origins = "*")
public class FacturacionController {

	private final FacturacionService facturacionService;

	public FacturacionController(FacturacionService facturacionService)
	{
		this.facturacionService = facturacionService;
	}

	@PostMapping
	public ResponseEntity<?> facturar()
	{
		facturacionService.facturar();
		return ResponseEntity.accepted().body("");
	}
	@GetMapping(path = "/periodos")
	public ResponseEntity<?> obtenerPeriodos()
	{
		return ResponseEntity.ok(facturacionService.obtenerPeriodos());
	}
}
