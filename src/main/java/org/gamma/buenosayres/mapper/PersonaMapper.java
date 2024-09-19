package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.ListarFamiliaDTO;
import org.gamma.buenosayres.dto.ListarPersonaDTO;
import org.gamma.buenosayres.dto.MiembrosFamiliaDTO;
import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonaMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public PersonaMapper()
	{
		TypeMap<Persona, ListarPersonaDTO> personaListarPersonaDTOTypeMap = modelMapper.createTypeMap(Persona.class, ListarPersonaDTO.class);
		personaListarPersonaDTOTypeMap.addMappings(mapper -> {
			mapper.map(src -> src.getFamilia().getId(), ListarPersonaDTO::setFamilia);
		});
	}
	public ListarPersonaDTO map(Persona persona)
	{
		ListarPersonaDTO personaDTO = modelMapper.map(persona, ListarPersonaDTO.class);
		if (persona.getUsuario() != null && persona.getUsuario().getRoles() != null) {
			personaDTO.setTipo(persona.getUsuario().getRoles().stream().map(rol -> rol.getAuthority().replace("ROLE_", "")).collect(Collectors.joining(" ")));
			//personaDTO.setTipo(persona.getUsuario().getRoles().stream().findAny().map(rol -> rol.getAuthority().replace("ROLE_", "")).orElse(""));
		}
		return personaDTO;
	}
	public List<ListarPersonaDTO> map(List<Persona> personas)
	{
		//return personas.stream().map(persona -> modelMapper.map(persona, ListarPersonaDTO.class)).toList();
		return personas.stream().map(this::map).toList();
	}
}
