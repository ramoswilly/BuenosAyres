package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "familias")
@NoArgsConstructor
@Getter
@Setter
public class Familia {
	@Id
	@GeneratedValue
	@Column(name = "id_familia")
	private UUID id;
	private String apellido;
	@OneToMany(mappedBy = "familia")
	List<Persona> miembros;
	public Familia(UUID id, List<Persona> miembros)
	{
		this.id = id;
		this.miembros = miembros;
	}
}
