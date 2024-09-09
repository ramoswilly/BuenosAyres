package org.gamma.buenosayres.rest.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.gamma.buenosayres.dto.FacturacionRequestDTO;
import org.gamma.buenosayres.mapper.FacturacionMapper;
import org.gamma.buenosayres.service.exception.ServiceException;
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
	public ResponseEntity<?> obtenerFacturas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate periodo)
	{
		return ResponseEntity.ok(facturacionService.obtenerFacturas(periodo).stream().map(facturacionMapper::map).toList());
		//TODO: manejar periodos invalidos
	}
	@GetMapping("/detalles")
	public ResponseEntity<?> obtenerFacturaPorId(@RequestParam UUID id)
	{
		try {
			return ResponseEntity.ok(facturacionMapper.map(facturacionService.obtenerFacturaPorId(id)));
		} catch(ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/deudas")
	public ResponseEntity<?> obtenerDeudas()
	{
		return ResponseEntity.ok(facturacionService.obtenerDeudas());
	}
	@PutMapping("/deudas")
	public ResponseEntity<?> actualizarDeudas(@RequestBody List<UUID> detallesAbonados)
	{
		return ResponseEntity.ok(
		facturacionService.actualizarDeudas(detallesAbonados)
			.stream()
			.map(facturacionMapper::map)
			.toList()
		);
	}
	@PostMapping
	public ResponseEntity<?> facturar(@RequestBody FacturacionRequestDTO request)
	{
		facturacionService.facturar(request);
		return ResponseEntity.accepted().body("");
	}
	@GetMapping("/familias")
	public ResponseEntity<?> familiasMasValiosas()
	{
		return ResponseEntity.ok(facturacionService.familiasMasValiosas().stream().map(facturacionMapper::map).toList());
	}
	@GetMapping("/ingresos")
	public ResponseEntity<?> ingresosPorMes()
	{
		return ResponseEntity.ok(facturacionService.ingresosPorMes());
	}
	@GetMapping("/periodos")
	public ResponseEntity<?> obtenerPeriodos()
	{
		return ResponseEntity.ok(facturacionService.obtenerPeriodos());
	}
}
