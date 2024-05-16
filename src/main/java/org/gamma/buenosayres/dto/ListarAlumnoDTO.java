package org.gamma.buenosayres.dto;

import lombok.*;
import org.gamma.buenosayres.model.Curso;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ListarAlumnoDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private Curso curso;
}