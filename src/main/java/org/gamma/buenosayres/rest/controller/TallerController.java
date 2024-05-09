package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.dto.TallerDTO;
import org.gamma.buenosayres.mapper.TallerMapper;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.gamma.buenosayres.service.implementation.TallerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/talleres")
public class TallerController {

	private final TallerService tallerService;
	private final TallerMapper tallerMapper;
	public TallerController(TallerService tallerService, TallerMapper tallerMapper)
	{
		this.tallerService = tallerService;
		this.tallerMapper = tallerMapper;
	}
	@GetMapping
	public List<TallerDTO> get()
	{
		return tallerService.getAll().stream().map(tallerMapper::map).toList();
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
