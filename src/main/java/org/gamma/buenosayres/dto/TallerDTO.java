package org.gamma.buenosayres.dto;

import lombok.*;
import org.gamma.buenosayres.model.Nivel;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TallerDTO {
	private UUID id;
	private String descripcion;
	private Nivel nivel;
	private List<UUID> alumnos;
}
