package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "alumnos")
@NoArgsConstructor
@Getter
@Setter
public class Alumno {

	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;
	@ManyToMany
	@JoinTable(
			name = "alumnos-talleres",
			joinColumns = @JoinColumn(name = "id_alumno"),
			inverseJoinColumns = @JoinColumn(name = "id_taller"))
	private List<Taller> talleres;
}