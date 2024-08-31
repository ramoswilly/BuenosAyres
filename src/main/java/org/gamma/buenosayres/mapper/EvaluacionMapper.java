package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.EvaluacionDTO;
import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.service.implementation.EvaluacionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public EvaluacionMapper()
	{
		TypeMap<Evaluacion, EvaluacionDTO> forwardMap = modelMapper.createTypeMap(Evaluacion.class, EvaluacionDTO.class);
		forwardMap.addMappings(mapper -> {
			mapper.map(src -> src.getMateria().getId(), EvaluacionDTO::setIdMateria);
			mapper.map(src -> src.getProfesor().getId(), EvaluacionDTO::setIdProfesor);
		});
	}
	public EvaluacionDTO map(Evaluacion evaluacion)
	{
		return modelMapper.map(evaluacion, EvaluacionDTO.class);
	}
}
