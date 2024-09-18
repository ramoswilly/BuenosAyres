package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/padres")
public class PadreViewController {
	@GetMapping("/index")
	public String index()
	{
		return "padres/alumnos";
	}
	@GetMapping("/alumnos")
	public String alumnos()
	{
		return "padres/alumnos";
	}
	@GetMapping("/deudas")
	public String deudas()
	{
		return "padres/deudas";
	}
	@GetMapping("/detalles-facturacion")
	public String detallesFacturacion()
	{
		return "padres/detalles-facturacion";
	}
	@GetMapping("/ver-calificaciones")
	public String verCalificaciones()
	{
		return "padres/ver-calificaciones";
	}
	@GetMapping("/inasistencias")
	public String inasistencias()
	{
		return "padres/inasistencias";
	}
}
