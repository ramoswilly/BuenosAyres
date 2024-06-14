package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dao.interfaces.*;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.model.TipoConcepto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FacturacionService {

	private final FamiliaDAO familiaDAO;
	private final ConceptoDAO conceptoDAO;
	private final AlumnoDAO alumnoDAO;
	private final TallerDAO tallerDAO;
	private final DetalleFacturaDAO detalleFacturaDAO;
	private final FacturaDAO facturaDAO;

	public FacturacionService(FamiliaDAO familiaDAO, ConceptoDAO conceptoDAO,
							  AlumnoDAO alumnoDAO, TallerDAO tallerDAO, DetalleFacturaDAO detalleFacturaDAO, FacturaDAO facturaDAO)
	{
		this.familiaDAO = familiaDAO;
		this.conceptoDAO = conceptoDAO;
		this.alumnoDAO = alumnoDAO;
		this.tallerDAO = tallerDAO;
		this.detalleFacturaDAO = detalleFacturaDAO;
		this.facturaDAO = facturaDAO;
	}
	private static final int[] porcentaje_descuento = {
			0, 10, 15, 50, 75, 100
	};
	private static DetalleFactura generarDetalle(Factura factura, Alumno alumno, Concepto concepto, int discount_index)
	{
		DetalleFactura detalleFactura = new DetalleFactura();
		detalleFactura.setFactura(factura);
		detalleFactura.setAlumno(alumno);
		detalleFactura.setConcepto(concepto);
		int porcentaje = porcentaje_descuento[discount_index % porcentaje_descuento.length];
		detalleFactura.setDescuento(porcentaje);
		detalleFactura.setMontoFinal(concepto.getMonto() * (100 - porcentaje) / 100);

		detalleFactura.setAbonado(false);
		return detalleFactura;
	}
	@Async
	@Transactional
	public void facturar(boolean conAdicionales, boolean conMatricula)
	{
		// Pre-obtener montos actuales
		// Cuota por Nivel
		Optional<Concepto> cuotaInicial = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.INICIAL, TipoConcepto.CUOTA);
		Optional<Concepto> cuotaPrimaria = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.PRIMARIA, TipoConcepto.CUOTA);
		Optional<Concepto> cuotaSecundaria = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.SECUNDARIA, TipoConcepto.CUOTA);
		Optional<Concepto> cuotaMateriales = conceptoDAO.findTopByTipoDeConceptoOrderByFechaActualizacionDesc(TipoConcepto.MATERIALES);
		// Fecha actual
		Date fechaFacturacion = new Date();

		// Cuota de Taller
		Map<Taller, Concepto> cuotaTallerMap = tallerDAO.findAll().stream()
				.map(conceptoDAO::findTopByTallerOrderByFechaActualizacionDesc)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(Concepto::getTaller, Function.identity()));
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
			factura.setPeriodo(fechaFacturacion);
			factura.setDetalles(new ArrayList<>());
			// Obtener un indice para iterar en descuentos
			AtomicInteger discount_index = new AtomicInteger();
			// Obtener conceptos
			alumnos.stream().filter(Optional::isPresent)
					.sorted(Comparator.comparingInt(a0 -> -a0.get().getCurso().getNivel().ordinal()))
					.forEach(alumno -> {
					switch (alumno.get().getCurso().getNivel()) {
						case INICIAL -> {
							// Detalle con Cuota de materiales
							DetalleFactura materiales = generarDetalle(factura, alumno.get(), cuotaMateriales.get(), 0);
							detalleFacturaDAO.save(materiales);
							factura.getDetalles().add(materiales);
							// Detalle cuota de nivel
							DetalleFactura cuota = generarDetalle(factura, alumno.get(), cuotaInicial.get(), discount_index.intValue());
							detalleFacturaDAO.save(cuota);
							factura.getDetalles().add(cuota);
						}
						case PRIMARIA -> {
							DetalleFactura cuota = generarDetalle(factura, alumno.get(), cuotaPrimaria.get(), discount_index.intValue());
							detalleFacturaDAO.save(cuota);
							factura.getDetalles().add(cuota);
						}
						case SECUNDARIA -> {
							DetalleFactura cuota = generarDetalle(factura, alumno.get(), cuotaSecundaria.get(), discount_index.intValue());
							detalleFacturaDAO.save(cuota);
							factura.getDetalles().add(cuota);
						}
					}
					discount_index.getAndIncrement(); // Siguiente hermano
			});
			// Obtener monto final
			float monto_final = factura.getDetalles().stream()
							.map(DetalleFactura::getMontoFinal)
							.reduce(0f, Float::sum);
			// Adjuntar monto final
			factura.setMontoFinal(monto_final);
			// Persistir factura
			facturaDAO.save(factura);
		}
	}
	public List<Date> obtenerPeriodos()
	{
		return facturaDAO.obtenerPeriodos();
	}
	public List<Factura> obtenerFacturas(Date periodo)
	{
		return facturaDAO.findAllByPeriodo(periodo);
	}
}
