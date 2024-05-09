package org.gamma.buenosayres.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoVisitor;

@Entity
@DiscriminatorValue("TALLER")
@Getter
@Setter
public class CuotaTaller extends Concepto {
	@ManyToOne
	@JoinColumn(name = "id_taller")
	private Taller taller;

	@Override
	public ConceptoDTO accept(ConceptoVisitor<ConceptoDTO> visitor)
	{
		return visitor.visit(this);
	}
}
