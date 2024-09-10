package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profesores")
public class ProfesorViewController {
	@GetMapping("/index")
	public String index()
	{
		return "/profesores/evaluaciones";
	}
	@GetMapping("/materias")
	public String materias()
	{
		return "profesores/materias";
	}
	@GetMapping("/evaluaciones")
	public String evaluaciones()
	{
		return "/profesores/evaluaciones";
	}
	@GetMapping("/crear-evaluacion")
	public String crearEvaluacion()
	{
		return "/profesores/crear-evaluacion";
	}
	@GetMapping("/evaluaciones-creadas")
	public String evaluacionesCreadas()
	{
		return "/profesores/evaluaciones-creadas";
	}
	@GetMapping("/ver-entregas")
	public String verEntregas()
	{
		return "/profesores/ver-entregas";
	}
}
