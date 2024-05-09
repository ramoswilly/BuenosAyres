package org.gamma.buenosayres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gamma.buenosayres.model.Nivel;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TallerDTO {
	private UUID id;
	private String descripcion;
	private Nivel nivel;
	private List<UUID> alumnos;
}
