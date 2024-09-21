package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.dto.ListarPersonaDTO;
import org.gamma.buenosayres.mapper.PersonaMapper;
import org.gamma.buenosayres.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personas")
@CrossOrigin(origins = "*")
public class PersonaController {
	private final PersonaService service;
	private final PersonaMapper mapper;
	@Autowired
	public PersonaController(PersonaService service, PersonaMapper mapper)
	{
		this.service = service;
		this.mapper = mapper;
	}
	@GetMapping
	public List<ListarPersonaDTO> getAll()
	{
		return mapper.map(service.getAll());
	}
}
