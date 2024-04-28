package org.gamma.buenosayres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActualizarAlumnoDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private UUID curso;
}