package org.gamma.buenosayres.mapper;

import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.model.*;

public interface ConceptoVisitor<T> {
	T visit(Concepto concepto);
	T visit(ConceptoAdicional concepto);
	T visit(Matricula concepto);
	T visit(CuotaTaller concepto);
	T visit(Materiales concepto);
	T visit(Cuota concepto);

}
