package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.CursoDTO;
import org.gamma.buenosayres.model.Curso;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public CursoMapper()
	{
		TypeMap<Curso, CursoDTO> forwardMapping = modelMapper.createTypeMap(Curso.class, CursoDTO.class);
		forwardMapping.addMappings(mapper -> {
			mapper.map(src -> src.getResponsable().getId(), CursoDTO::setId_responsable);
			mapper.map(src -> src.getResponsable().getPersona().getNombre(), CursoDTO::setNombre_responsable);
			mapper.map(src -> src.getResponsable().getPersona().getApellido(), CursoDTO::setApellido_responsable);
		});

	}
	public CursoDTO map(Curso curso)
	{
		return modelMapper.map(curso, CursoDTO.class);
	}
}
