package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConceptoMapper implements ConceptoVisitor<ConceptoDTO> {
	private static final ModelMapper modelMapper = new ModelMapper();
	public ConceptoMapper()
	{
		TypeMap<CuotaTaller, ConceptoDTO> tallerConceptoDTO = modelMapper.createTypeMap(CuotaTaller.class, ConceptoDTO.class);
		tallerConceptoDTO.addMappings(mapper -> {
			mapper.map(src -> src.getTaller().getId(), ConceptoDTO::setTaller);
		});
		TypeMap<ConceptoDTO, CuotaTaller> conceptoDTOTaller = modelMapper.createTypeMap(ConceptoDTO.class, CuotaTaller.class);
		conceptoDTOTaller.addMappings(mapper -> {
			mapper.map(ConceptoDTO::getMonto, CuotaTaller::setMonto);
			mapper.map(ConceptoDTO::getNivel, CuotaTaller::setNivel);
			mapper.map(ConceptoDTO::getTaller, (dest, value) -> {
				dest.getTaller().setId((UUID) value);
			});
		});
	}
	public Concepto map(ConceptoDTO concepto)
	{
		switch (concepto.getTipoDeConcepto()) {
			case "MATRICULA":
				return modelMapper.map(concepto, Matricula.class);
			case "CUOTA":
				return modelMapper.map(concepto, Cuota.class);
			case "MATERIALES":
				return modelMapper.map(concepto, Materiales.class);
			case "TALLER":
				return modelMapper.map(concepto, CuotaTaller.class);
			case "ADICIONAL":
				return modelMapper.map(concepto, ConceptoAdicional.class);
		}
		throw new IllegalArgumentException("Tipo de concepto invalido.");
	}
	public ConceptoDTO map(Concepto concepto)
	{
		return visit(concepto);
	}
	public ConceptoDTO visit(Concepto concepto)
	{
		return concepto.accept(this);
	}
	@Override
	public ConceptoDTO visit(ConceptoAdicional concepto)
	{
		return modelMapper.map(concepto, ConceptoDTO.class);
	}
	@Override
	public ConceptoDTO visit(Matricula concepto)
	{
		return modelMapper.map(concepto, ConceptoDTO.class);
	}
	@Override
	public ConceptoDTO visit(CuotaTaller concepto)
	{
		return modelMapper.map(concepto, ConceptoDTO.class);
	}
	@Override
	public ConceptoDTO visit(Materiales concepto)
	{
		return modelMapper.map(concepto, ConceptoDTO.class);
	}
	@Override
	public ConceptoDTO visit(Cuota concepto)
	{
		return modelMapper.map(concepto, ConceptoDTO.class);
	}
}
