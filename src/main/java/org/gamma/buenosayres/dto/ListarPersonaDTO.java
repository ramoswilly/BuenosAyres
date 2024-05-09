package org.gamma.buenosayres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gamma.buenosayres.model.TipoPersona;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListarPersonaDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private TipoPersona tipo;
	private UUID familia;
}
