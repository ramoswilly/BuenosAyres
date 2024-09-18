package org.gamma.buenosayres.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	@Autowired
	private final CustomAuthenticationSuccessHandler successHandler;

	public SecurityConfig(CustomAuthenticationSuccessHandler successHandler)
	{
		this.successHandler = successHandler;
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}

	@Bean
	@SuppressWarnings("removal")
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
						.requestMatchers("/assets/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/profesores/**").hasRole("PROFESOR")
						.requestMatchers("/padres/**").hasRole("PADRE")
						.requestMatchers("/alumnos/**").hasRole("ALUMNO")
						.requestMatchers("/director/**").hasRole("DIRECTOR")
						.requestMatchers("/preceptores/**").hasRole("PRECEPTOR")
						.requestMatchers("/login/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(formLogin ->
						formLogin
								.loginPage("/login") // Página personalizada de login
								.loginProcessingUrl("/perform_login") // URL a la cual se envían las credenciales para su validación
								//.defaultSuccessUrl("/home", true) // Página a la que se redirige tras un inicio de sesión exitoso
								.failureUrl("/login?error=true") // Página a la que se redirige tras un fallo de login
								.successHandler(successHandler)
								.permitAll()
				)
				.logout(logout ->
						logout
								.logoutUrl("/perform_logout") // URL para realizar el logout
								.logoutSuccessUrl("/login?logout=true") // Página a la que se redirige tras el logout
								.permitAll()
				);

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}