package org.gamma.buenosayres.service;

import org.gamma.buenosayres.repository.ConceptoDAO;
import org.gamma.buenosayres.repository.TallerDAO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConceptoService {
	private final ConceptoDAO conceptoDAO;
	private final TallerDAO tallerDAO;

	public ConceptoService(ConceptoDAO conceptoDAO, TallerDAO tallerDAO)
	{
		this.conceptoDAO = conceptoDAO;
		this.tallerDAO = tallerDAO;
	}
	public Page<Concepto> get(TipoConcepto tipo, Nivel nivel, UUID id_taller, int limit) throws ServiceException
	{
		Taller taller = null;
		if (id_taller != null) {
		taller = tallerDAO.findById(id_taller).orElseThrow(
					() -> new ServiceException("Taller Inexistente", 400)
			);
		}

		return conceptoDAO.findByTipoConceptoAndNivelAndTallerOrderByFechaActualizacionDesc(tipo, nivel, taller, PageRequest.of(0, limit));
	}
	public Concepto newConcepto(Concepto concepto) throws ServiceException
	{
		if (concepto.getTipoDeConcepto() == TipoConcepto.TALLER) {
			tallerDAO.findById(concepto.getTaller().getId()).orElseThrow(
					() -> new ServiceException("Taller Inexistente", 400)
			);
		}
		concepto.setFechaActualizacion(new Date());
		return conceptoDAO.save(concepto);
	}
}
