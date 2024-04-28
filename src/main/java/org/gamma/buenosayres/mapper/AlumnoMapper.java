package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.dto.ActualizarAlumnoDTO;
import org.gamma.buenosayres.dto.CrearAlumnoDTO;
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
		TypeMap<Alumno, ListarAlumnoDTO> alumnoToListarAlumnoDTOMapper = modelMapper.createTypeMap(Alumno.class, ListarAlumnoDTO.class);
		TypeMap<CrearAlumnoDTO, Alumno> crearAlumnoDTOToAlumnoMapper = modelMapper.createTypeMap(CrearAlumnoDTO.class, Alumno.class);
		TypeMap<ActualizarAlumnoDTO, Alumno> actualizarAlumnoDTOToAlumnoMapper = modelMapper.createTypeMap(ActualizarAlumnoDTO.class, Alumno.class);

		alumnoToListarAlumnoDTOMapper.addMappings(mapper -> {
			mapper.map(src -> src.getPersona().getDni(), ListarAlumnoDTO::setDni);
			mapper.map(src -> src.getPersona().getNombre(), ListarAlumnoDTO::setNombre);
			mapper.map(src -> src.getPersona().getApellido(), ListarAlumnoDTO::setApellido);
			mapper.map(src -> src.getPersona().getDireccion(), ListarAlumnoDTO::setDireccion);
		});

		crearAlumnoDTOToAlumnoMapper.addMappings(mapper -> {
			mapper.map(CrearAlumnoDTO::getDni, (dest, value) -> dest.getPersona().setDni((String) value));
			mapper.map(CrearAlumnoDTO::getNombre, (dest, value) -> dest.getPersona().setNombre((String) value));
			mapper.map(CrearAlumnoDTO::getApellido, (dest, value) -> dest.getPersona().setApellido((String) value));
			mapper.map(CrearAlumnoDTO::getDireccion, (dest, value) -> dest.getPersona().setDireccion((String) value));
			mapper.map(CrearAlumnoDTO::getCurso, (dest, value) -> dest.getCurso().setId((UUID) value));
		});

		actualizarAlumnoDTOToAlumnoMapper.addMappings(mapper -> {
			mapper.map(ActualizarAlumnoDTO::getDni, (dest, value) -> dest.getPersona().setDni((String) value));
			mapper.map(ActualizarAlumnoDTO::getNombre, (dest, value) -> dest.getPersona().setNombre((String) value));
			mapper.map(ActualizarAlumnoDTO::getApellido, (dest, value) -> dest.getPersona().setApellido((String) value));
			mapper.map(ActualizarAlumnoDTO::getDireccion, (dest, value) -> dest.getPersona().setDireccion((String) value));
			mapper.map(ActualizarAlumnoDTO::getCurso, (dest, value) -> dest.getCurso().setId((UUID) value));
		});
	}
	public Alumno map(CrearAlumnoDTO alumno)
	{
		return modelMapper.map(alumno, Alumno.class);
	}
	public ListarAlumnoDTO map(Alumno alumno)
	{
		return modelMapper.map(alumno, ListarAlumnoDTO.class);
	}
	public Alumno map(ActualizarAlumnoDTO alumno)
	{
		return modelMapper.map(alumno, Alumno.class);
	}
}
