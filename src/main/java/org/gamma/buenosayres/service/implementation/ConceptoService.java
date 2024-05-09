package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.dao.interfaces.ConceptoDAO;
import org.gamma.buenosayres.dao.interfaces.TallerDAO;
import org.gamma.buenosayres.model.*;
import org.gamma.buenosayres.service.exception.ServiceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.Date;
import java.util.List;

@Service
public class ConceptoService {
	private final ConceptoDAO conceptoDAO;
	private final TallerDAO tallerDAO;

	public ConceptoService(ConceptoDAO conceptoDAO, TallerDAO tallerDAO)
	{
		this.conceptoDAO = conceptoDAO;
		this.tallerDAO = tallerDAO;
	}
	public List<Concepto> get(String tipo, int limit)
	{
		if (tipo == null) {
			return conceptoDAO.findAllBy(PageRequest.of(0, limit,
					Sort.by("fechaActualizacion").descending()));
		}
		return conceptoDAO.findByTipoDeConcepto(tipo,
				PageRequest.of(0, limit,
						Sort.by("fechaActualizacion").descending()));
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
