package org.gamma.buenosayres.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.gamma.buenosayres.model.Nivel;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConceptoDTO {
	private UUID id;
	private float monto;
	private Date fechaActualizacion;
	private String tipoDeConcepto;
	private Nivel nivel;
	private UUID taller;
}
