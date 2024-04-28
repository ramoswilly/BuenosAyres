package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Responsable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/padres")
public class ResponsableController {

	@GetMapping(produces = "application/json")
	public List<Alumno> getAllResponsables()
	{
		//return service.findAll();
		return new ArrayList<>();
	}
	@PostMapping
	public ResponseEntity<String> newResponsable(@RequestBody Responsable responsable)
	{
		/*try {
			service.newResponsable(responsable);
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Padre registrado\n");*/

		return ResponseEntity.status(200).body("");
	}
}
