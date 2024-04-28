package org.gamma.buenosayres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Turno;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CursoAlumnoDTO {
	private int orden;
	private Nivel nivel;
	private Turno turno;
}
