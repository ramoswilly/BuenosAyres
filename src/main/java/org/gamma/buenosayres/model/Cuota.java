package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Entity
@Table(name = "cuotas")
@NoArgsConstructor
@Getter
@Setter
public class Cuota {

	@Id
	@GeneratedValue
	@Column(name = "id_cuota")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_concepto")
	private Concepto concepto;
	@Enumerated(EnumType.STRING)
	Nivel nivel;

}
