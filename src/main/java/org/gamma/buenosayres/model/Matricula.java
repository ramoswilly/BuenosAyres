package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "matriculas")
@NoArgsConstructor
@Getter
@Setter
public class Matricula {
	@Id
	@GeneratedValue
	@Column(name = "id_matricula")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_concepto")
	private Concepto concepto;
	private Nivel nivel;
}
