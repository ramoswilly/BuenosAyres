package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoAcceptor;
import org.gamma.buenosayres.mapper.ConceptoVisitor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "conceptos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_concepto", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Getter
@Setter
public abstract class Concepto implements ConceptoAcceptor<ConceptoDTO> {
	@Id
	@GeneratedValue
	@Column(name = "id_concepto")
	private UUID id;
	@Column(name = "monto")
	private float monto;
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;
	@Column(name = "tipo_de_concepto", insertable = false, updatable = false)
	String tipoDeConcepto;
	public Concepto(UUID id, float monto, Date fechaActualizacion)
	{
		this.id = id;
		this.monto = monto;
		this.fechaActualizacion = fechaActualizacion;
	}

}