package org.gamma.buenosayres.rest.controller;

import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.service.implementation.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {
	private final CursoService service;

	@Autowired
	public CursoController(CursoService service)
	{
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public List<Curso> getAllCursos()
	{
		return service.findAll();
	}
}
