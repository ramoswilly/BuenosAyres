package org.gamma.buenosayres.service.implementation;

import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.dao.interfaces.CursoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
	private final CursoDAO cursoDAO;
	@Autowired
	public CursoService(CursoDAO cursoDAO)
	{
		this.cursoDAO = cursoDAO;
	}
	public List<Curso> findAll()
	{
		return cursoDAO.findAll();
	}
}
