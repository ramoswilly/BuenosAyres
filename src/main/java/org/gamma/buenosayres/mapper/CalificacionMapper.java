package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.CalificacionDTO;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.model.Calificacion;
import org.gamma.buenosayres.model.Concepto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CalificacionMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public CalificacionMapper()
	{
		TypeMap<Calificacion, CalificacionDTO> mapper = modelMapper.createTypeMap(Calificacion.class, CalificacionDTO.class);
		mapper.addMappings(mapping -> {
			mapping.map(src -> src.getEvaluacion().getId(), CalificacionDTO::setIdEvaluacion);
			mapping.map(src -> src.getAlumno().getId(), CalificacionDTO::setIdAlumno);
			mapping.map(Calificacion::getNota, CalificacionDTO::setNota);
		});
	}
	public CalificacionDTO map(Calificacion calificacion)
	{
		return modelMapper.map(calificacion, CalificacionDTO.class);
	}
}
