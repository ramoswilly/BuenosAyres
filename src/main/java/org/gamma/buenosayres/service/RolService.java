package org.gamma.buenosayres.service;

import org.gamma.buenosayres.repository.RolDAO;
import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.model.TipoEmpleado;
import org.springframework.stereotype.Service;

@Service
public class RolService {
	private final RolDAO dao;

	public RolService(RolDAO dao)
	{
		this.dao = dao;
	}

	public Rol find(TipoEmpleado rol)
	{
		return dao.findByAuthority(rol.name())
				.orElseThrow(RuntimeException::new);
		//TODO: Exception
	}
}
