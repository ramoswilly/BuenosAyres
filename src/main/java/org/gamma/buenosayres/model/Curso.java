package org.gamma.buenosayres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Curso {
	@Id
	@Column(name = "id_curso")
	private UUID id;
	private int orden;
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	@Enumerated(EnumType.STRING)
	private Turno turno;
	@OneToMany(mappedBy = "curso")
	@JsonIgnore
	private List<Alumno> alumnos;
}
