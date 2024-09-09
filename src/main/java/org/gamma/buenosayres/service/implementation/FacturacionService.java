package org.gamma.buenosayres.service.implementation;

import jakarta.transaction.Transactional;
import org.gamma.buenosayres.dao.interfaces.*;
import org.gamma.buenosayres.dto.DeudaDTO;
import org.gamma.buenosayres.dto.FacturacionRequestDTO;
import org.gamma.buenosayres.dto.IngresosResponse;
import org.gamma.buenosayres.mapper.FacturacionMapper;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
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
	private final PadreDAO padreDAO;
	private final UserService userService;
	private final FacturacionMapper facturacionMapper;

	public FacturacionService(FamiliaDAO familiaDAO, ConceptoDAO conceptoDAO, AlumnoDAO alumnoDAO, TallerDAO tallerDAO,
							  DetalleFacturaDAO detalleFacturaDAO, FacturaDAO facturaDAO, PadreDAO padreDAO,
							  UserService userService, FacturacionMapper facturacionMapper) {
        this.familiaDAO = familiaDAO;
        this.conceptoDAO = conceptoDAO;
        this.alumnoDAO = alumnoDAO;
        this.tallerDAO = tallerDAO;
        this.detalleFacturaDAO = detalleFacturaDAO;
        this.facturaDAO = facturaDAO;
        this.padreDAO = padreDAO;
		this.userService = userService;
		this.facturacionMapper = facturacionMapper;
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
	public void facturar(FacturacionRequestDTO request) {
		// Pre-obtener montos actuales
		// Cuota por Nivel
		Optional<Concepto> cuotaInicial = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.INICIAL, TipoConcepto.CUOTA);
		Optional<Concepto> cuotaPrimaria = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.PRIMARIA, TipoConcepto.CUOTA);
		Optional<Concepto> cuotaSecundaria = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.SECUNDARIA, TipoConcepto.CUOTA);
		// Materiales
		Optional<Concepto> cuotaMateriales = conceptoDAO.findTopByTipoDeConceptoOrderByFechaActualizacionDesc(TipoConcepto.MATERIALES);
		// Pre-obtener adicionales
		List<Concepto> adicionales = request.getAdicionales().stream()
				.map(id -> conceptoDAO.findById(id))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		// Lista de adicionales por nivel
		List<Concepto> adicionalInicial = adicionales.stream().filter(adicional -> adicional.getNivel() == Nivel.INICIAL).toList();
		List<Concepto> adicionalPrimaria = adicionales.stream().filter(adicional -> adicional.getNivel() == Nivel.PRIMARIA).toList();
		List<Concepto> adicionalSecundaria = adicionales.stream().filter(adicional -> adicional.getNivel() == Nivel.SECUNDARIA).toList();

		// Fecha facturacion
		// Validar si ya existe periodo
		LocalDate fechaFacturacion = request.getPeriodo();
		// Buscar por mes y día
		Optional<LocalDate> periodoExistente = facturaDAO.obtenerPeriodos().stream()
				.filter(periodo -> periodo.getMonth().equals(request.getPeriodo().getMonth()))
				.findFirst();
		if (periodoExistente.isPresent())
			System.out.println(periodoExistente.get());
		// ¿ya se facturó este mes?
		if (periodoExistente.isPresent()) {
			fechaFacturacion = periodoExistente.get();
		}
		// Cuota de Taller
		Map<Taller, Concepto> cuotaTallerMap = tallerDAO.findAll().stream()
				.map(conceptoDAO::findTopByTallerOrderByFechaActualizacionDesc)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(Concepto::getTaller, Function.identity()));
		// Matriculas
		Optional<Concepto> matriculaInicial = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.INICIAL, TipoConcepto.MATRICULA);
		Optional<Concepto> matriculaPrimaria = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.PRIMARIA, TipoConcepto.MATRICULA);
		Optional<Concepto> matriculaSecundaria = conceptoDAO.findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel.SECUNDARIA, TipoConcepto.MATRICULA);
		// Obtener todas las familias
		List<Familia> familias = familiaDAO.findAll().stream().filter(Familia::isHabilitada).filter(familia -> familia.getMiembros().size() > 0).toList();
		for (Familia familia : familias) {

			// Obtener alumnos en familias
			List<Optional<Alumno>> alumnos = familia.getMiembros().stream()
					.filter(alumno -> userService.hasRole(alumno.getUsuario(), "ROLE_ALUMNO"))
					// Convertir persona a alumno con hibernate
					.map(miembro -> alumnoDAO.findById(miembro.getId()))
					.toList();
			// Factura
			Factura factura = new Factura();
			if (periodoExistente.isPresent()) {
				Optional<Factura> facturaExistente = facturaDAO.findByFamiliaAndPeriodo(familia, fechaFacturacion);
				if (facturaExistente.isPresent()) {
					factura = facturaExistente.get();
					factura.getDetalles().clear();
				}
			} else {
				factura.setDetalles(new ArrayList<>());
			}
			factura.setPeriodo(fechaFacturacion);

			// Guardar la factura antes de agregar los detalles
			factura = facturaDAO.save(factura);

			// Obtener un indice para iterar en descuentos
			int discount_index = 0;

			List<Optional<Alumno>> alumnosPresentes = alumnos.stream().filter(Optional::isPresent).toList();

			// Crear una nueva lista mutable a partir de alumnosPresentes
			List<Optional<Alumno>> alumnosOrdenables = new ArrayList<>(alumnosPresentes);

			alumnosOrdenables.sort(Comparator.comparingInt(a0 -> -a0.get().getCurso().getNivel().ordinal()));

			for (Optional<Alumno> alumnoOptional : alumnosOrdenables) {
				Alumno alumno = alumnoOptional.get();

				switch (alumno.getCurso().getNivel()) {
					case INICIAL:
						// Detalle con Cuota de materiales
						DetalleFactura materiales = generarDetalle(factura, alumno, cuotaMateriales.get(), 0);
						detalleFacturaDAO.save(materiales);
						factura.getDetalles().add(materiales);
						// Detalle cuota de nivel
						DetalleFactura cuota = generarDetalle(factura, alumno, cuotaInicial.get(), discount_index);
						detalleFacturaDAO.save(cuota);
						factura.getDetalles().add(cuota);
						// Aplicar adicionales
						for (Concepto adicional : adicionalInicial) {
							DetalleFactura detalleAdicional = generarDetalle(factura, alumno, adicional, 0);
							detalleFacturaDAO.save(detalleAdicional);
							factura.getDetalles().add(detalleAdicional);
						}
						// Está en un taller?
						for (Taller taller : alumno.getTalleres()) {
							if (cuotaTallerMap.containsKey(taller)) {
								DetalleFactura detalleTaller = generarDetalle(factura, alumno, cuotaTallerMap.get(taller), 0);
								detalleFacturaDAO.save(detalleTaller);
								factura.getDetalles().add(detalleTaller);
							}
						}
						// Cuando se habilita la facturacion de matriculas, facturar las mismas
						if (request.facturarMatricula()) {
							DetalleFactura detalleMatricula = generarDetalle(factura, alumno, matriculaInicial.get(), 0);
							detalleFacturaDAO.save(detalleMatricula);
							factura.getDetalles().add(detalleMatricula);
						}
						break;
					case PRIMARIA:
						// Detalle cuota de nivel
						cuota = generarDetalle(factura, alumno, cuotaPrimaria.get(), discount_index);
						detalleFacturaDAO.save(cuota);
						factura.getDetalles().add(cuota);
						// Aplicar adicionales
						for (Concepto adicional : adicionalPrimaria) {
							DetalleFactura detalleAdicional = generarDetalle(factura, alumno, adicional, 0);
							detalleFacturaDAO.save(detalleAdicional);
							factura.getDetalles().add(detalleAdicional);
						}
						// Está en un taller?
						for (Taller taller : alumno.getTalleres()) {
							if (cuotaTallerMap.containsKey(taller)) {
								DetalleFactura detalleTaller = generarDetalle(factura, alumno, cuotaTallerMap.get(taller), 0);
								detalleFacturaDAO.save(detalleTaller);
								factura.getDetalles().add(detalleTaller);
							}
						}
						// Cuando se habilita la facturacion de matriculas, facturar las mismas
						if (request.facturarMatricula()) {
							DetalleFactura detalleMatricula = generarDetalle(factura, alumno, matriculaPrimaria.get(), 0);
							detalleFacturaDAO.save(detalleMatricula);
							factura.getDetalles().add(detalleMatricula);
						}
						break;
					case SECUNDARIA:
						// Detalle cuota de nivel
						cuota = generarDetalle(factura, alumno, cuotaSecundaria.get(), discount_index);
						detalleFacturaDAO.save(cuota);
						factura.getDetalles().add(cuota);
						// Aplicar adicionales
						for (Concepto adicional : adicionalSecundaria) {
							DetalleFactura detalleAdicional = generarDetalle(factura, alumno, adicional, 0);
							detalleFacturaDAO.save(detalleAdicional);
							factura.getDetalles().add(detalleAdicional);
						}
						// Está en un taller?
						for (Taller taller : alumno.getTalleres()) {
							if (cuotaTallerMap.containsKey(taller)) {
								DetalleFactura detalleTaller = generarDetalle(factura, alumno, cuotaTallerMap.get(taller), 0);
								detalleFacturaDAO.save(detalleTaller);
								factura.getDetalles().add(detalleTaller);
							}
						}
						// Cuando se habilita la facturacion de matriculas, facturar las mismas
						if (request.facturarMatricula()) {
							DetalleFactura detalleMatricula = generarDetalle(factura, alumno, matriculaSecundaria.get(), 0);
							detalleFacturaDAO.save(detalleMatricula);
							factura.getDetalles().add(detalleMatricula);
						}
						break;
				}
				discount_index++; // Siguiente hermano
			}

			// Obtener monto final
			float monto_final = 0f;
			for (DetalleFactura detalle : factura.getDetalles()) {
				monto_final += detalle.getMontoFinal();
			}

			// Adjuntar monto final
			factura.setMontoFinal(monto_final);
			// Setear familia
			factura.setFamilia(familia);
			// Setear responsable de facturacion
			Optional<Padre> padre = familia.getMiembros().stream()
					.filter(persona -> userService.hasRole(persona.getUsuario(), "ROLE_PADRE"))
					.map(persona -> padreDAO.findById(persona.getId()))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(Padre::isResponsableFacturacion)
					.findAny();
			if (padre.isPresent())
				factura.setFacturado(padre.get());
			// No es necesario volver a guardar la factura, ya se guardó antes
			// facturaDAO.save(factura);
		}
	}

	public List<LocalDate> obtenerPeriodos()
	{
		return facturaDAO.obtenerPeriodos();
	}
	public List<Factura> obtenerFacturas(LocalDate periodo)
	{
		return facturaDAO.findAllByPeriodo(periodo);
	}
	public Factura obtenerFacturaPorId(UUID id) throws ServiceException {
		return facturaDAO.findById(id).orElseThrow(() -> new ServiceException("Id de Factura invalido", 404));
	}
	@Transactional
	public List<DetalleFactura> actualizarDeudas(List<UUID> detallesAbonados) {
		Date fecha = new Date();
		return detalleFacturaDAO.findAllById(detallesAbonados)
			.stream()
			.map(detalle -> {
					detalle.setAbonado(true);
					detalle.setFechaPago(fecha);
					detalleFacturaDAO.save(detalle);
					return detalle;
				})
			.toList();
	}
    public List<DeudaDTO> obtenerDeudas() {
        List<Factura> facturas = facturaDAO.facturasImpagas();
		Map<Familia, List<Factura>> facturasMap = facturas.stream()
			.collect(Collectors.groupingBy(Factura::getFamilia));
		return facturasMap.entrySet().stream()
			.map(entry -> {
					Familia familia = entry.getKey();
					Double deudaTotal = entry.getValue().stream()
						.flatMap(factura -> factura.getDetalles().stream())
						.filter(detalle -> !detalle.isAbonado())
						.mapToDouble(DetalleFactura::getMontoFinal)
						.sum();

                    DeudaDTO deudaDTO = new DeudaDTO();
                    deudaDTO.setIdFamilia(familia.getId());
                    deudaDTO.setApellidoFamilia(familia.getApellido());
                    deudaDTO.setDeudaTotal(deudaTotal.floatValue());
                    deudaDTO.setFacturas(entry.getValue().stream()
                            .map(facturacionMapper::map)
                            .collect(Collectors.toList()));
                    return deudaDTO;
				})
			.toList();
    }

	public List<Factura> familiasMasValiosas()
	{
		return facturaDAO.findTop10Familias();
	}

	public List<IngresosResponse> ingresosPorMes()
	{
		return facturaDAO.ingresosPorMes();
	}
}
