package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.dto.ListarAlumnoDTO;
import org.gamma.buenosayres.dto.TallerDTO;
import org.gamma.buenosayres.mapper.AlumnoMapper;
import org.gamma.buenosayres.mapper.TallerMapper;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.TallerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/talleres")
@CrossOrigin(origins = "*")
public class TallerController {

	private final TallerService tallerService;
	private final TallerMapper tallerMapper;
	private final AlumnoMapper alumnoMapper;
	public TallerController(TallerService tallerService, TallerMapper tallerMapper, AlumnoMapper alumnoMapper)
	{
		this.tallerService = tallerService;
		this.tallerMapper = tallerMapper;
		this.alumnoMapper = alumnoMapper;
	}
	@GetMapping
	public List<TallerDTO> get()
	{
		return tallerService.getAll().stream().map(tallerMapper::map).toList();
	}
	@GetMapping
	@RequestMapping("/{id_taller}/alumnos")
	public ResponseEntity<?> alumnosEnTaller(@PathVariable UUID id_taller)
	{
		try {
			return ResponseEntity.ok(tallerService.getAlumnosInTaller(id_taller).stream().map(alumnoMapper::map).toList());
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PutMapping(path = "/{id_taller}/alumnos")
	public ResponseEntity<?> altaAlumnos(@PathVariable UUID id_taller, @RequestBody TallerDTO alumnos)
	{
		try {
			return ResponseEntity.ok(tallerService.altaAlumnos(id_taller, tallerMapper.map(alumnos)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping
	public ResponseEntity<?> altaTaller(@RequestBody TallerDTO dto)
	{
		try {
			return ResponseEntity.ok(tallerService.newTaller(tallerMapper.map(dto)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
