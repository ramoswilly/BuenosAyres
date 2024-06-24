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
