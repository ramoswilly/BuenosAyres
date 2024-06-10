package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.dto.ActualizarAlumnoDTO;
import org.gamma.buenosayres.dto.AlumnoDTO;
import org.gamma.buenosayres.dto.ListarAlumnoDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AlumnoMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public AlumnoMapper()
	{
		TypeMap<Alumno, AlumnoDTO> alumnoToAlumnoDTOMapper = modelMapper.createTypeMap(Alumno.class, AlumnoDTO.class);
		TypeMap<AlumnoDTO, Alumno> alumnoDTOToAlumnoMapper = modelMapper.createTypeMap(AlumnoDTO.class, Alumno.class);

		alumnoToAlumnoDTOMapper.addMappings(mapper -> {
			mapper.map(src -> src.getPersona().getDni(), AlumnoDTO::setDni);
			mapper.map(src -> src.getPersona().getNombre(), AlumnoDTO::setNombre);
			mapper.map(src -> src.getPersona().getApellido(), AlumnoDTO::setApellido);
			mapper.map(src -> src.getPersona().getDireccion(), AlumnoDTO::setDireccion);
			mapper.map(Alumno::getCurso, AlumnoDTO::setCurso);
		});

		alumnoDTOToAlumnoMapper.addMappings(mapper -> {
			mapper.map(AlumnoDTO::getDni, (dest, value) -> dest.getPersona().setDni((String) value));
			mapper.map(AlumnoDTO::getNombre, (dest, value) -> dest.getPersona().setNombre((String) value));
			mapper.map(AlumnoDTO::getApellido, (dest, value) -> dest.getPersona().setApellido((String) value));
			mapper.map(AlumnoDTO::getDireccion, (dest, value) -> dest.getPersona().setDireccion((String) value));
			mapper.map(AlumnoDTO::getCurso, (dest, value) -> dest.getCurso().setId((UUID) value));
		});
	}
	public Alumno map(AlumnoDTO alumno)
	{
		return modelMapper.map(alumno, Alumno.class);
	}

	public AlumnoDTO map(Alumno alumno)
	{
		return modelMapper.map(alumno, AlumnoDTO.class);
	}
}
