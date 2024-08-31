package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.EntregaDTO;
import org.gamma.buenosayres.model.Entrega;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class EntregaMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public EntregaMapper() {
		TypeMap<Entrega, EntregaDTO> forwardMap = modelMapper.createTypeMap(Entrega.class, EntregaDTO.class);
		forwardMap.addMappings(mapper -> {
			mapper.map(src -> src.getEvaluacion().getId(), EntregaDTO::setIdEvaluacion);
			mapper.map(src -> src.getAlumno().getId(), EntregaDTO::setIdAlumno);
			mapper.map(src -> src.getAlumno().getPersona().getNombre(), EntregaDTO::setNombreAlumno);
			mapper.map(src -> src.getAlumno().getPersona().getApellido(), EntregaDTO::setApellidoAlumno);
		});
	}

	public EntregaDTO map(Entrega entrega) {
		return modelMapper.map(entrega, EntregaDTO.class);
	}
}