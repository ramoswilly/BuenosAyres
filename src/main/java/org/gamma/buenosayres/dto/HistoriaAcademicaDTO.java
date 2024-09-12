package org.gamma.buenosayres.dto;

import java.util.List;

public class HistoriaAcademicaDTO {
	private List<HistoriaAcademicaMateriaDTO> materias;

	public List<HistoriaAcademicaMateriaDTO> getMaterias()
	{
		return materias;
	}

	public void setMaterias(List<HistoriaAcademicaMateriaDTO> materias)
	{
		this.materias = materias;
	}
}
