package org.gamma.buenosayres.mapper;
public interface ConceptoAcceptor<T> {
	T accept(ConceptoVisitor<T> visitor);
}
