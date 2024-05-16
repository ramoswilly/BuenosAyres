package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.AlumnoDAO;
import org.gamma.buenosayres.dao.interfaces.CuotaDAO;
import org.gamma.buenosayres.dao.interfaces.FamiliaDAO;
import org.gamma.buenosayres.dao.interfaces.MaterialesDAO;
import org.gamma.buenosayres.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturacionService {

	private final FamiliaDAO familiaDAO;
	private final AlumnoDAO alumnoDAO;
	private final CuotaDAO cuotaDAO;
	private final MaterialesDAO materialesDAO;

	public FacturacionService(FamiliaDAO familiaDAO, AlumnoDAO alumnoDAO, CuotaDAO cuotaDAO, MaterialesDAO materialesDAO)
	{
		this.familiaDAO = familiaDAO;
		this.alumnoDAO = alumnoDAO;
		this.cuotaDAO = cuotaDAO;
		this.materialesDAO = materialesDAO;
	}

	@Async
	public void facturar()
	{
		// Pre-obtener montos actuales
		// Cuota por nivel
		Optional<Cuota> cuotaInicial = cuotaDAO.findTopByNivelOrderByFechaActualizacion(Nivel.INICIAL);
		Optional<Cuota> cuotaPrimaria = cuotaDAO.findTopByNivelOrderByFechaActualizacion(Nivel.PRIMARIA);
		Optional<Cuota> cuotaSecundaria = cuotaDAO.findTopByNivelOrderByFechaActualizacion(Nivel.SECUNDARIA);
		Optional<Materiales> cuotaMateriales = materialesDAO.findTop();

		// Obtener todas las familias
		List<Familia> familias = familiaDAO.findAll();

		for (Familia familia : familias) {
			// Obtener alumnos en familias
			List<Optional<Alumno>> alumnos = familia.getMiembros().stream()
					.filter(miembro -> miembro.getTipo() == TipoPersona.ALUMNO)
					// Convertir persona a alumno con hibernate
					.map(miembro -> alumnoDAO.findById(miembro.getId()))
					.toList();

			// Factura
			Factura factura = new Factura();

			// Obtener conceptos
			alumnos.stream().filter(Optional::isPresent).forEach(alumno -> {
					switch (alumno.get().getCurso().getNivel()) {
						case INICIAL -> {
							// Detalle con Cuota
							DetalleFactura detalleCuota = new DetalleFactura();
							detalleCuota.setFactura(factura);
							detalleCuota.setAlumno(alumno.get());
							detalleCuota.setAbonado(false);
							detalleCuota.setConcepto(cuotaInicial.get());
							// Agregar cuota de materiales
							DetalleFactura detalleMateriales = new DetalleFactura();
							detalleMateriales.setFactura(factura);
							detalleMateriales.setAlumno(alumno.get());
							detalleMateriales.setAbonado(false);
							detalleMateriales.setConcepto(cuotaMateriales.get());
						}
						case PRIMARIA -> {

						}
						case SECUNDARIA -> {
						}
					}
			});
		}
	}
}
