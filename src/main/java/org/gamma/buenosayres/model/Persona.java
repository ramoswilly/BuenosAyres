package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "personas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Persona {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	@Enumerated(EnumType.STRING)
	private TipoPersona tipo;
	@ManyToOne
	@JoinColumn(name = "id_familia")
	private Familia familia;
}
