package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.ConceptoDAO;
import org.gamma.buenosayres.dao.interfaces.CuotaTallerDAO;
import org.gamma.buenosayres.dao.interfaces.TallerDAO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConceptoService {
	private final ConceptoDAO conceptoDAO;
	private final TallerDAO tallerDAO;
	private final CuotaTallerDAO cuotaTallerDAO;

	public ConceptoService(ConceptoDAO conceptoDAO, TallerDAO tallerDAO, CuotaTallerDAO cuotaTallerDAO)
	{
		this.conceptoDAO = conceptoDAO;
		this.tallerDAO = tallerDAO;
		this.cuotaTallerDAO = cuotaTallerDAO;
	}
	public Page<Concepto> get(String tipo, String nivel, UUID id_taller, int limit) throws ServiceException
	{

		Nivel eNivel = Nivel.INICIAL;
		if (nivel != null && !nivel.isEmpty()) {
			try {
				eNivel = Nivel.valueOf(nivel);
			} catch (IllegalArgumentException e) {
				throw new ServiceException("Nivel invalido", 400);
			}
		}
		if (id_taller != null) {
			Taller taller = tallerDAO.findById(id_taller).orElseThrow(
					() -> new ServiceException("Taller Inexistente", 400)
			);
			return cuotaTallerDAO.findByTallerOrderByFechaActualizacionDesc(taller, PageRequest.of(0, limit));
		}
		if ((tipo == null || tipo.isEmpty()) && (nivel == null || nivel.isEmpty())) {
			return conceptoDAO.findAll(PageRequest.of(0, limit));
		} else if (tipo == null || tipo.isEmpty()) {
			return conceptoDAO.findByNivelOrderByFechaActualizacionDesc(eNivel, PageRequest.of(0, limit));
		} else if (nivel == null || nivel.isEmpty()) {
			return conceptoDAO.findByTipoDeConceptoOrderByFechaActualizacionDesc(tipo, PageRequest.of(0, limit));
		} else {
			return conceptoDAO.findByTipoDeConceptoAndNivelOrderByFechaActualizacionDesc(tipo, eNivel, PageRequest.of(0, limit));
		}
	}
	public Concepto newConcepto(Concepto concepto) throws ServiceException
	{
		if (concepto instanceof CuotaTaller) {
			tallerDAO.findById(((CuotaTaller)concepto).getTaller().getId()).orElseThrow(
					() -> new ServiceException("Taller Inexistente", 400)
			);
		}
		concepto.setFechaActualizacion(new Date());
		return conceptoDAO.save(concepto);
	}
}
