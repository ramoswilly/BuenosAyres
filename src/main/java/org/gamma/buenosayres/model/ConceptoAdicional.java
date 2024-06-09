package org.gamma.buenosayres.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoVisitor;

@Entity
@DiscriminatorValue(value = "ADICIONAL")
public class ConceptoAdicional extends Concepto {
	public ConceptoAdicional()
	{
	}

	@Override
	public ConceptoDTO accept(ConceptoVisitor<ConceptoDTO> visitor)
	{
		return visitor.visit(this);
	}
	private String descripcion;

	public String getDescripcion()
	{
		return this.descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
}
