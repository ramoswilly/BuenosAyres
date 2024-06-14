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
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	TipoConcepto tipoDeConcepto;
	@Column(name = "nivel")
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	@ManyToOne
	@JoinColumn(name = "id_taller")
	private Taller taller;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public TipoConcepto getTipoDeConcepto() {
		return tipoDeConcepto;
	}
	public void setTipoDeConcepto(TipoConcepto tipoDeConcepto) {
		this.tipoDeConcepto = tipoDeConcepto;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	public Taller getTaller() {
		return taller;
	}
	public void setTaller(Taller taller) {
		this.taller = taller;
	}

}
