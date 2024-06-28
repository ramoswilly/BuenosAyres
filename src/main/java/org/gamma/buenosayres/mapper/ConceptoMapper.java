package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConceptoMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public ConceptoMapper()
	{
		TypeMap<ConceptoDTO, Concepto> dtoToConcepto
			= modelMapper.createTypeMap(ConceptoDTO.class, Concepto.class);
		dtoToConcepto.addMappings(mapper -> {
				mapper.map(ConceptoDTO::getTaller, (dest, val) -> dest.getTaller().setId((UUID)val));
		});
		TypeMap<Concepto, ConceptoDTO> conceptoToDTO =
			modelMapper.createTypeMap(Concepto.class, ConceptoDTO.class);
		conceptoToDTO.addMappings(mapper -> {
				mapper.map(src -> src.getTaller().getId(), ConceptoDTO::setTaller);
		});
	}
	public Concepto map(ConceptoDTO concepto)
	{
		return modelMapper.map(concepto, Concepto.class);
	}
	public ConceptoDTO map(Concepto concepto)
	{
		return modelMapper.map(concepto, ConceptoDTO.class);
	}
}
