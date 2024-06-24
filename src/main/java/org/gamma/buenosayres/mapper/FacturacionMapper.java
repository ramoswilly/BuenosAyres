package org.gamma.buenosayres.mapper;

import java.util.ArrayList;
import java.util.List;

import org.gamma.buenosayres.dto.AlumnoDTO;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.dto.DetalleFacturaDTO;
import org.gamma.buenosayres.dto.FacturaDTO;
import org.gamma.buenosayres.model.DetalleFactura;
import org.gamma.buenosayres.model.Factura;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class FacturacionMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    private ConceptoMapper conceptoMapper;
    private AlumnoMapper alumnoMapper;

    public FacturacionMapper(ConceptoMapper conceptoMapper, AlumnoMapper alumnoMapper)
    {
        this.conceptoMapper = conceptoMapper;
        this.alumnoMapper = alumnoMapper;
        Converter<List<DetalleFactura>, List<DetalleFacturaDTO>> converter = new AbstractConverter<>() {
                private DetalleFacturaDTO map(DetalleFactura detalle)
                {
                    DetalleFacturaDTO dto = new DetalleFacturaDTO();
                    dto.setConcepto(conceptoMapper.map(detalle.getConcepto()));
                    dto.setAlumno(alumnoMapper.map(detalle.getAlumno()));
                    dto.setDescuento(detalle.getDescuento());
                    dto.setAbonado(false);
                    dto.setFechaPago(detalle.getFechaPago());
                    return dto;
                }
                protected List<DetalleFacturaDTO> convert(List<DetalleFactura> source)
                {
                    if (source == null) return new ArrayList<>();
                    return source.stream().map(this::map).toList();
                }
        };

        TypeMap<Factura, FacturaDTO> facturaToDTOMapper =
            modelMapper.createTypeMap(Factura.class, FacturaDTO.class);
        facturaToDTOMapper.addMappings(mapper -> {
                mapper.using(converter).map(Factura::getDetalles, FacturaDTO::setDetalles);
                mapper.map(src -> src.getFacturado().getId(), FacturaDTO::setId_facturado);
                mapper.map(src -> src.getFacturado().getPersona().getDni(), FacturaDTO::setDni_facturado);
                mapper.map(src -> src.getFacturado().getPersona().getNombre(), FacturaDTO::setNombre_facturado);
                mapper.map(src -> src.getFacturado().getPersona().getApellido(), FacturaDTO::setApellido_facturado);
                mapper.map(src -> src.getFamilia().getApellido(), FacturaDTO::setApellido_familia);
        });
    }
    public FacturaDTO map(Factura factura)
    {
        System.out.println(factura.getDetalles().get(0).getAlumno().getPersona().getNombre());
        return modelMapper.map(factura, FacturaDTO.class);
    }
    public DetalleFacturaDTO map(DetalleFactura detalle)
    {
        return modelMapper.map(detalle, DetalleFacturaDTO.class);
    }
}
