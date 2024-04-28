package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "conceptos")
@NoArgsConstructor
@Getter
@Setter
public class Concepto {
	@Id
	@GeneratedValue
	@Column(name = "id_concepto")
	private UUID id;
	@Column(name = "monto")
	private float monto;
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;

	public Concepto(UUID id, float monto, Date fechaActualizacion)
	{
		this.id = id;
		this.monto = monto;
		this.fechaActualizacion = fechaActualizacion;
	}
}