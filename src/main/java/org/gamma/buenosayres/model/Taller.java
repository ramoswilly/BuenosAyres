package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "talleres")
@NoArgsConstructor
@Getter
@Setter
public class Taller {
	@Id
	@GeneratedValue
	@Column(name = "id_taller")
	private UUID id;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	@ManyToMany(mappedBy = "talleres")
	private List<Alumno> alumnos;
}
