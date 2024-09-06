package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.SancionDTO;
import org.gamma.buenosayres.model.Sancion;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SancionMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public SancionMapper()
	{
		TypeMap<Sancion, SancionDTO> forwardMapper = modelMapper.createTypeMap(Sancion.class, SancionDTO.class);
		TypeMap<SancionDTO, Sancion> inverseMapper = modelMapper.createTypeMap(SancionDTO.class, Sancion.class);
		forwardMapper.addMappings(mapper -> {
			mapper.map(src -> src.getAlumno().getId(), SancionDTO::setAlumnoId);
			mapper.map(src -> src.getAlumno().getPersona().getNombre(), SancionDTO::setNombre_alumno);
			mapper.map(src -> src.getAlumno().getPersona().getApellido(), SancionDTO::setApellido_alumno);
		});
		inverseMapper.addMappings(mapper -> mapper.map(SancionDTO::getAlumnoId, (dest, value) -> dest.getAlumno().setId((UUID) value)));
	}
	public Sancion map(SancionDTO sancion)
	{
		return modelMapper.map(sancion, Sancion.class);
	}
	public SancionDTO map(Sancion sancion)
	{
		return modelMapper.map(sancion, SancionDTO.class);
	}
}