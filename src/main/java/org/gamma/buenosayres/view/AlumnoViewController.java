package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/alumnos")
public class AlumnoViewController {
	@GetMapping("/index")
	public String index()
	{
		return "alumnos/calificaciones";
	}
	@GetMapping("/evaluaciones")
	public String materias()
	{
		return "alumnos/evaluaciones";
	}
	@GetMapping("/calificaciones")
	public String evaluaciones()
	{
		return "alumnos/calificaciones";
	}
	@GetMapping("/evaluaciones-creadas")
	public String evaluacionesCreadas()
	{
		return "alumnos/evaluaciones-creadas";
	}
	@GetMapping("/realizar-entrega")
	public String realizarEntrega()
	{
		return "alumnos/realizar-entrega";
	}
}
