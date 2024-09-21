package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.SaludDTO;
import org.gamma.buenosayres.model.Salud;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class SaludMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public SaludMapper() {
		TypeMap<Salud, SaludDTO> saludToSaludDTOTypeMap = modelMapper.createTypeMap(Salud.class, SaludDTO.class);
		saludToSaludDTOTypeMap.addMappings(mapper -> {
			mapper.map(src -> src.getPersona().getDni(), SaludDTO::setDni);
			mapper.map(src -> src.getPersona().getNombre(), SaludDTO::setNombre);
			mapper.map(src -> src.getPersona().getApellido(), SaludDTO::setApellido);
			mapper.map(Salud::getObraSocial, SaludDTO::setObraSocial);
			mapper.map(Salud::getNumeroAfiliado, SaludDTO::setNumeroAfiliado);
		});
	}

	public SaludDTO map(Salud salud) {
		return modelMapper.map(salud, SaludDTO.class);
	}
}