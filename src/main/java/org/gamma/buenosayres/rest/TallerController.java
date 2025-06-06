package org.gamma.buenosayres.rest;


import org.gamma.buenosayres.dto.CrearTallerDTO;
import org.gamma.buenosayres.dto.TallerDTO;
import org.gamma.buenosayres.mapper.AlumnoMapper;
import org.gamma.buenosayres.mapper.TallerMapper;
import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.TallerService;
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
	public ResponseEntity<?> altaAlumnos(@PathVariable UUID id_taller, @RequestBody List<UUID> alumnos)
	{
		try {
			tallerService.altaAlumnos(id_taller, alumnos);
			return ResponseEntity.ok("Agregados");
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@DeleteMapping(path = "/{id_taller}/alumnos")
	public ResponseEntity<?> removerAlumno(@PathVariable UUID id_taller, @RequestBody UUID alumno)
	{
		try {
			tallerService.removerAlumno(id_taller, alumno);
			return ResponseEntity.ok("Eliminados");
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@GetMapping("/{idTaller}")
	public ResponseEntity<?> get(@PathVariable(value = "idTaller") UUID idTaller) {
		try {
			return ResponseEntity.ok(tallerService.getTallerById(idTaller));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PutMapping("/{idTaller}")
	public ResponseEntity<?> update(@PathVariable(value = "idTaller") UUID idTaller, @RequestBody CrearTallerDTO tallerDTO) {
		try {
			return ResponseEntity.ok(tallerService.updateTaller(idTaller, tallerDTO));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	@PostMapping
	public ResponseEntity<?> altaTaller(@RequestBody CrearTallerDTO dto)
	{
		try {
			return ResponseEntity.ok(tallerMapper.map(tallerService.newTaller(dto)));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
