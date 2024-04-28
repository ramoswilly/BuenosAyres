package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cuotas_de_taller")
@NoArgsConstructor
@Getter
@Setter
public class CuotaTaller {
	@Id
	@GeneratedValue
	@Column(name = "id_cuota_taller")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_concepto")
	private Concepto concepto;
	@ManyToOne
	@JoinColumn(name = "id_taller")
	private Taller taller;
}