package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.InasistenciaDTO;
import org.gamma.buenosayres.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InasistenciaMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public InasistenciaMapper() {
		TypeMap<InasistenciaDTO, Inasistencia> dtoToModel = modelMapper.createTypeMap(InasistenciaDTO.class, Inasistencia.class);
		dtoToModel.addMappings(mapper -> mapper.map(InasistenciaDTO::getAlumnoId, (dest, value) -> dest.getAlumno().setId((UUID) value)));

		TypeMap<Inasistencia, InasistenciaDTO> modelToDto = modelMapper.createTypeMap(Inasistencia.class, InasistenciaDTO.class);
		modelToDto.addMappings(mapper -> {
			mapper.map(src -> src.getAlumno().getId(), InasistenciaDTO::setAlumnoId);
			mapper.map(src -> src.getAlumno().getPersona().getNombre(), InasistenciaDTO::setNombre);
			mapper.map(src -> src.getAlumno().getPersona().getApellido(), InasistenciaDTO::setApellido);
			mapper.map(src -> src.getAlumno().getPersona().getDni(), InasistenciaDTO::setDni);
		});
	}

	public Inasistencia map(InasistenciaDTO dto) {
		return modelMapper.map(dto, Inasistencia.class);
	}

	public InasistenciaDTO map(Inasistencia model) {
		return modelMapper.map(model, InasistenciaDTO.class);
	}
}