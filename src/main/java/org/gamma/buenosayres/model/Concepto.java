package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "conceptos")
public class Concepto {
	@Id
	@GeneratedValue
	@Column(name = "id_concepto")
	private UUID id;
	@Column(name = "monto")
	private float monto;
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;
	@Column(name = "tipo", updatable = false)
	tipoConcepto tipoDeConcepto;
	@Column(name = "nivel")
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	@ManyToOne
	@JoinColumn(name = "id_taller")
	private Taller taller;
}
