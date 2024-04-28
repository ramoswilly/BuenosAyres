package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.dto.ListarFamiliaDTO;
import org.gamma.buenosayres.dto.MiembrosFamiliaDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
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

	}
	public ListarFamiliaDTO map(Familia familia)
	{
		return modelMapper.map(familia, ListarFamiliaDTO.class);
	}
	public List<ListarFamiliaDTO> map(List<Familia> familias)
	{
		return familias.stream().map(this::map).toList();
	}

}
