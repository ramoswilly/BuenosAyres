package org.gamma.buenosayres.security;

import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

	private Usuario usuario;

	public CustomUserDetails(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Rol rol : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.isEnabled();
	}

	public Usuario getUsuario() {
		return usuario;
	}
}