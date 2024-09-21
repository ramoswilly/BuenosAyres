package org.gamma.buenosayres.service;

import org.gamma.buenosayres.model.Patologia;
import org.gamma.buenosayres.repository.PatologiaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatologiaService {

	@Autowired
	private PatologiaDAO patologiaDAO;

	public List<Patologia> obtenerPatologias() {
		return patologiaDAO.findAll();
	}
}