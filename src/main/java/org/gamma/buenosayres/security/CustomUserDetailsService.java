package org.gamma.buenosayres.security;

import org.gamma.buenosayres.model.Usuario;
import org.gamma.buenosayres.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		if (!usuario.get().isEnabled()) {
			throw new UsernameNotFoundException("Usuario no habilitado");
		}
		return new CustomUserDetails(usuario.get());
	}
}