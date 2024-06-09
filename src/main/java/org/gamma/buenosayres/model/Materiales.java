package org.gamma.buenosayres.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoVisitor;

@Entity
@DiscriminatorValue(value = "MATERIALES")
public class Materiales extends Concepto {
	public Materiales()
	{
	}

	@Override
	public ConceptoDTO accept(ConceptoVisitor<ConceptoDTO> visitor)
	{
		return visitor.visit(this);
	}
}
