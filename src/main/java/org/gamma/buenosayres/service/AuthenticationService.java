package org.gamma.buenosayres.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {
	public boolean hasRole(Authentication authentication, String rol)
	{
		return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(rol));
	}

}
