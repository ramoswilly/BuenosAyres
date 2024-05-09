package org.gamma.buenosayres.mapper;

import jakarta.persistence.Convert;
import org.gamma.buenosayres.dto.ListarFamiliaDTO;
import org.gamma.buenosayres.dto.MiembrosFamiliaDTO;
import org.gamma.buenosayres.dto.TallerDTO;
import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.model.Taller;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TallerMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	public TallerMapper()
	{
		Converter<List<Alumno>, List<UUID>> converterAlumnoUUID = new AbstractConverter<>() {
			protected List<UUID> convert(List<Alumno> source)
			{
				if (source == null) return new ArrayList<>();
				return source.stream().map(Alumno::getId).toList();
			}
		};

		TypeMap<Taller, TallerDTO> tallerDTOTypeMap = modelMapper.createTypeMap(Taller.class, TallerDTO.class);
		tallerDTOTypeMap.addMappings(
				mapper -> {
					mapper.using(converterAlumnoUUID).map(Taller::getAlumnos, TallerDTO::setAlumnos);
				}
		);
		Converter<List<UUID>, List<Alumno>> converterUUIDAlumno = new AbstractConverter<List<UUID>, List<Alumno>>() {
			@Override
			protected List<Alumno> convert(List<UUID> uuids)
			{
				return uuids.stream().map(uuid -> {
					Alumno alumno = new Alumno();
					alumno.setId(uuid);
					return alumno;
				}).toList();
			}
		};
		TypeMap<TallerDTO, Taller> dtoTallerTypeMap = modelMapper.createTypeMap(TallerDTO.class, Taller.class);
		dtoTallerTypeMap.addMappings(
				mapper -> {
					mapper.skip(Taller::setId);
					mapper.using(converterAlumnoUUID).map(TallerDTO::getAlumnos, Taller::setAlumnos);
				}
		);
	}
	public TallerDTO map(Taller taller)
	{
		return modelMapper.map(taller, TallerDTO.class);
	}
	public Taller map(TallerDTO taller)
	{
		return modelMapper.map(taller, Taller.class);
	}
}
