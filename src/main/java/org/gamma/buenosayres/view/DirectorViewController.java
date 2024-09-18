package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/director")
public class DirectorViewController {
	@GetMapping("/index")
	public String index()
	{
		return "director/calificaciones";
	}
	@GetMapping("/calificaciones")
	public String evaluaciones()
	{
		return "director/calificaciones";
	}
	@GetMapping("/detalle-alumno")
	public String detalles()
	{
		return "director/detalle-alumno";
	}
}