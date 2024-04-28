package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "materiales")
@NoArgsConstructor
@Getter
@Setter
public class Materiales {
	@Id
	@GeneratedValue
	@Column(name = "id_materiales")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_concepto")
	private Concepto concepto;
}
