package org.gamma.buenosayres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActualizarFamiliaDTO {
	private UUID id;
	private String apellido;
	boolean habilitada;
	private List<UUID> miembros;
}
