package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.ActualizarFamiliaDTO;
import org.gamma.buenosayres.dto.FamiliaDTO;
import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.dto.ListarFamiliaDTO;
import org.gamma.buenosayres.dto.MiembrosFamiliaDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FamiliaMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public FamiliaMapper()
	{
		Converter<List<Persona>, List<MiembrosFamiliaDTO>> converterPersonaMiembro = new AbstractConverter<>() {
			protected List<MiembrosFamiliaDTO> convert(List<Persona> source)
			{
				return source.stream().map(persona -> modelMapper.map(persona, MiembrosFamiliaDTO.class)).toList();
			}
		};
		TypeMap<Familia, ListarFamiliaDTO> familiaListarFamiliaDTOTypeMap = modelMapper.createTypeMap(Familia.class, ListarFamiliaDTO.class);
		familiaListarFamiliaDTOTypeMap.addMappings(mapper -> mapper.using(converterPersonaMiembro).map(Familia::getMiembros, ListarFamiliaDTO::setMiembros));


	Converter<List<UUID>, List<Persona>> converterUUIDPersona = new AbstractConverter<List<UUID>, List<Persona>>() {
			@Override
			protected List<Persona> convert(List<UUID> source)
			{
				if (source == null) return new ArrayList<>();
				return source.stream().map(uuid -> {
					Persona persona = new Persona();
					persona.setId(uuid);
					return persona;
				}).toList();
			}
		};

		TypeMap<ActualizarFamiliaDTO, Familia> actualizarFamiliaDTOFamiliaTypeMap = modelMapper.createTypeMap(ActualizarFamiliaDTO.class, Familia.class);
		actualizarFamiliaDTOFamiliaTypeMap.addMappings(mapper -> {
			mapper.using(converterUUIDPersona).map(ActualizarFamiliaDTO::getMiembros, Familia::setMiembros);
		});

	}
	public FamiliaDTO map(Familia familia)
	{
		return modelMapper.map(familia, FamiliaDTO.class);
	}
	public List<FamiliaDTO> map(List<Familia> familias)
	{
		return familias.stream().map(this::map).toList();
	}
	public Familia map(FamiliaDTO familia)
	{
		return modelMapper.map(familia, Familia.class);
	}

	public Familia map(UUID familia, Map<String, List<UUID>> miembros)
	{
		Familia lFamilia = new Familia();
		lFamilia.setId(familia);
		lFamilia.setMiembros(
		miembros.getOrDefault("miembros", new ArrayList<>()).stream().map(
				miembro -> {
					Persona persona = new Persona();
					persona.setId(miembro);
					persona.setFamilia(lFamilia);
					return persona;
				}
		).toList());
		return lFamilia;
	}
	public MiembrosFamiliaDTO map(Persona persona)
	{
		MiembrosFamiliaDTO miembrosFamiliaDTO = new MiembrosFamiliaDTO();
		miembrosFamiliaDTO.setApellido(persona.getApellido());
		miembrosFamiliaDTO.setNombre(persona.getNombre());
		miembrosFamiliaDTO.setId(persona.getId());
		miembrosFamiliaDTO.setDni(persona.getDni());
		miembrosFamiliaDTO.setTipo(persona.getUsuario().getRoles().stream().map(rol -> rol.getAuthority().replace("ROLE_", "")).collect(Collectors.joining(" ")));
		//miembrosFamiliaDTO.setTipo(persona.getUsuario().getRoles().stream().findAny().map(rol -> rol.getAuthority().replace("ROLE_", "")).orElse(""));
		miembrosFamiliaDTO.setDireccion(persona.getDireccion());
		return miembrosFamiliaDTO;
	}
	public List<MiembrosFamiliaDTO> mapTo(List<Persona> personas)
	{
		//return personas.stream().map(persona -> modelMapper.map(persona, MiembrosFamiliaDTO.class)).toList();
		return personas.stream().map(this::map).toList();
	}
}
