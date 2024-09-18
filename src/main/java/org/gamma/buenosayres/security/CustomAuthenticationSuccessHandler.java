package org.gamma.buenosayres.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			response.sendRedirect("/admin/index");
		}
		// Verifica si el usuario tiene el rol "DIRECTOR"
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DIRECTOR"))) {
			// Redirige a la página de directores
			response.sendRedirect("/director/index");
		}
		// Verifica si el usuario tiene el rol "PROFESOR"
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESOR"))) {
			// Redirige a la página de profesores
			response.sendRedirect("/profesores/index");
		}
		// Verifica si el usuario tiene el rol "ALUMNO"
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ALUMNO"))) {
			// Redirige a la página de alumnos
			response.sendRedirect("/alumnos/index");
		}
		// Verifica si el usuario tiene el rol "PADRE"
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PADRE"))) {
			// Redirige a la página de alumnos
			response.sendRedirect("/padres/index");
		}
		// Verifica si el usuario tiene el rol "PRECEPTOR"
		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRECEPTOR"))) {
			// Redirige a la página de alumnos
			response.sendRedirect("/preceptores/index");
		}
	}
}
