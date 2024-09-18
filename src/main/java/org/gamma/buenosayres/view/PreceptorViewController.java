package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/preceptores")
public class PreceptorViewController {
	@GetMapping("/index")
	public String index()
	{
		return "preceptores/calificaciones";
	}
	@GetMapping("/calificaciones")
	public String calificaciones()
	{
		return "preceptores/calificaciones";
	}
	@GetMapping("/detalle-alumno")
	public String detalleAlumno()
	{
		return "preceptores/detalle-alumno";
	}
	@GetMapping("/salud")
	public String salud()
	{
		return "preceptores/salud";
	}
	@GetMapping("/editar-informacion-salud")
	public String editarInformacionSalud()
	{
		return "preceptores/editar-informacion-salud";
	}
	@GetMapping("/disciplina")
	public String disciplina()
	{
		return "preceptores/sanciones";
	}
	@GetMapping("/editar-sancion")
	public String editarSancion()
	{
		return "preceptores/editar-sancion";
	}
	@GetMapping("/agregar-sancion")
	public String agregarSancion()
	{
		return "preceptores/agregar-sancion";
	}
	@GetMapping("/inasistencias")
	public String inasistencias()
	{
		return "preceptores/inasistencias";
	}
	@GetMapping("/agregar-inasistencia")
	public String agregarInasistencia()
	{
		return "preceptores/agregar-inasistencia";
	}
	@GetMapping("/editar-inasistencia")
	public String editarInasistencia()
	{
		return "preceptores/editar-inasistencia";
	}
}
