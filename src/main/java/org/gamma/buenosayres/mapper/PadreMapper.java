package org.gamma.buenosayres.mapper;

import java.util.UUID;

import org.gamma.buenosayres.dto.PadreDTO;
import org.gamma.buenosayres.model.Padre;
import org.gamma.buenosayres.model.TipoPersona;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
@Component
public class PadreMapper {
     private static final ModelMapper modelMapper = new ModelMapper();
     public PadreMapper() {
          TypeMap<Padre, PadreDTO> padreToDTOMap = modelMapper.createTypeMap(Padre.class, PadreDTO.class);
          TypeMap<PadreDTO, Padre> DTOtoPadreMap = modelMapper.createTypeMap(PadreDTO.class, Padre.class);

          padreToDTOMap.addMappings(mapper -> {
               mapper.map(src -> src.getPersona().getId(), PadreDTO::setId);
               mapper.map(src -> src.getPersona().getDni(), PadreDTO::setDni);
               mapper.map(src -> src.getPersona().getNombre(), PadreDTO::setNombre);
               mapper.map(src -> src.getPersona().getApellido(), PadreDTO::setApellido);
               mapper.map(src -> src.getPersona().getDireccion(), PadreDTO::setDireccion);
               mapper.map(Padre::getCuit, PadreDTO::setCuit);
          });

          DTOtoPadreMap.addMappings(mapper -> {
               mapper.map(PadreDTO::getId, (dest, value) -> dest.getPersona().setId((UUID) value));
               mapper.map(PadreDTO::getDni, (dest, value) -> dest.getPersona().setDni((String)value));
               mapper.map(PadreDTO::getNombre, (dest, value) -> dest.getPersona().setNombre((String)value));
               mapper.map(PadreDTO::getApellido, (dest, value) -> dest.getPersona().setApellido((String)value));
               mapper.map(PadreDTO::getDireccion, (dest, value) -> dest.getPersona().setDireccion((String)value));
               mapper.map(PadreDTO::getCuit, Padre::setCuit);
          });
     }
     public PadreDTO map(Padre padre)
     {
          return modelMapper.map(padre, PadreDTO.class);
     }
     public Padre map(PadreDTO padre) {
          System.out.println(padre.getCuit());
          return modelMapper.map(padre, Padre.class);
     }
}
