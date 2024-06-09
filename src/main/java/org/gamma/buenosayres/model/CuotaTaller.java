package org.gamma.buenosayres.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoVisitor;

@Entity
@DiscriminatorValue("TALLER")
public class CuotaTaller extends Concepto {
	@ManyToOne
	@JoinColumn(name = "id_taller")
	private Taller taller;
	@Override
	public ConceptoDTO accept(ConceptoVisitor<ConceptoDTO> visitor)
	{
		return visitor.visit(this);
	}

	public Taller getTaller()
	{
		return this.taller;
	}

	public void setTaller(Taller taller)
	{
		this.taller = taller;
	}
}
