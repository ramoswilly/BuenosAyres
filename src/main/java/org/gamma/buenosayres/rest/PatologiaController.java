package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.model.Patologia;
import org.gamma.buenosayres.service.PatologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patologias")
@CrossOrigin(origins = "*")
public class PatologiaController {

	@Autowired
	private PatologiaService patologiaService;

	@GetMapping
	public ResponseEntity<List<Patologia>> obtenerPatologias() {
		return ResponseEntity.ok(patologiaService.obtenerPatologias());
	}
}