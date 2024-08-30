package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.MateriaDTO;
import org.gamma.buenosayres.model.Materia;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MateriaMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public MateriaMapper()
	{
		TypeMap<Materia, MateriaDTO> forwardMapper = modelMapper.createTypeMap(Materia.class, MateriaDTO.class);
		TypeMap<MateriaDTO, Materia> inverseMapper = modelMapper.createTypeMap(MateriaDTO.class, Materia.class);
		forwardMapper.addMappings(mapper -> {
			mapper.map(src -> src.getCurso().getId(), MateriaDTO::setId_curso);
			mapper.map(src -> src.getCurso().getOrden(), MateriaDTO::setOrden_curso);
			mapper.map(src -> src.getCurso().getNivel(), MateriaDTO::setNivel_curso);
			mapper.map(src -> src.getCurso().getTurno(), MateriaDTO::setTurno_curso);
			mapper.map(src -> src.getProfesor().getId(), MateriaDTO::setId_profesor);
			mapper.map(src -> src.getProfesor().getPersona().getNombre(), MateriaDTO::setNombre_profesor);
			mapper.map(src -> src.getProfesor().getPersona().getApellido(), MateriaDTO::setApellido_profesor);
		});
		inverseMapper.addMappings(mapper -> {
			mapper.map(MateriaDTO::getId_curso, (dest, value) -> dest.getCurso().setId((UUID) value));
			mapper.map(MateriaDTO::getId_profesor, (dest, value) -> dest.getProfesor().setId((UUID) value));
		});
	}
	public Materia map(MateriaDTO materia)
	{
		return modelMapper.map(materia, Materia.class);
	}
	public MateriaDTO map(Materia materia)
	{
		if (materia == null) return null;
		return modelMapper.map(materia, MateriaDTO.class);
	}
}
