package org.gamma.buenosayres.rest.controller;

import java.util.Date;

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
	@GetMapping
	public ResponseEntity<?> obtenerFacturas(@RequestParam Date periodo)
	{
		return ResponseEntity.ok(0);
//		facturacionService.obtenerFacturas(periodo);
	}
	@PostMapping
	public ResponseEntity<?> facturar(@RequestParam(required = false, defaultValue = "false") boolean adicionales,
									  @RequestParam(required = false,  defaultValue = "false") boolean matriculas)
	{
		facturacionService.facturar(adicionales, matriculas);
		return ResponseEntity.accepted().body("");
	}
	@GetMapping(path = "/periodos")
	public ResponseEntity<?> obtenerPeriodos()
	{
		return ResponseEntity.ok(facturacionService.obtenerPeriodos());
	}
}
