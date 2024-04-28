package org.gamma.buenosayres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListarFamiliaDTO {
	private UUID id;
	private String apellido;
	private List<MiembrosFamiliaDTO> miembros;
}
