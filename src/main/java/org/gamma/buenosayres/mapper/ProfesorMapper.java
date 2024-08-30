package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.ProfesorDTO;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.model.Rol;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class ProfesorMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public ProfesorMapper()
	{
		TypeMap<Profesor, ProfesorDTO> forwardMapping = modelMapper.createTypeMap(Profesor.class, ProfesorDTO.class);
		TypeMap<ProfesorDTO, Profesor> inverseMapping = modelMapper.createTypeMap(ProfesorDTO.class, Profesor.class);

		forwardMapping.addMappings(mapper -> {
			mapper.map(src -> src.getPersona().getDni(), ProfesorDTO::setDni);
			mapper.map(src -> src.getPersona().getNombre(), ProfesorDTO::setNombre);
			mapper.map(src -> src.getPersona().getApellido(), ProfesorDTO::setApellido);
			mapper.map(src -> src.getPersona().getDireccion(), ProfesorDTO::setDireccion);
			mapper.map(src -> src.getCUIL(), ProfesorDTO::setCuil);
			mapper.map(src -> src.getTelefono(), ProfesorDTO::setTelefono);
			mapper.map(src -> src.getEmail(), ProfesorDTO::setEmail);
			mapper.map(src -> src.getNivel(), ProfesorDTO::setNivel);
		});
		inverseMapping.addMappings(mapper -> {
			mapper.map(ProfesorDTO::getDni, (dest, value) -> dest.getPersona().setDni((String) value));
			mapper.map(ProfesorDTO::getNombre, (dest, value) -> dest.getPersona().setNombre((String) value));
			mapper.map(ProfesorDTO::getApellido, (dest, value) -> dest.getPersona().setApellido((String) value));
			mapper.map(ProfesorDTO::getDireccion, (dest, value) -> dest.getPersona().setDireccion((String) value));
			mapper.map(ProfesorDTO::getCuil, Profesor::setCUIL);
			mapper.map(ProfesorDTO::getTelefono, Profesor::setTelefono);
			mapper.map(ProfesorDTO::getEmail, Profesor::setEmail);
			mapper.map(ProfesorDTO::getNivel, Profesor::setNivel);
		});
	}
	public ProfesorDTO map(Profesor profesor)
	{
		return modelMapper.map(profesor, ProfesorDTO.class);
	}
	public Profesor map(ProfesorDTO profesor)
	{
		return modelMapper.map(profesor, Profesor.class);
	}
}
