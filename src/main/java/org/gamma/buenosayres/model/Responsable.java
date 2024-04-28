package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "responsables")
@NoArgsConstructor
@Getter
@Setter
public class Responsable {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	private String telefono;
	private String email;
	private boolean responsableFacturacion;

}
