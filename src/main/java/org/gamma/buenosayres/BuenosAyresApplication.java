package org.gamma.buenosayres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class BuenosAyresApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(BuenosAyresApplication.class, args);
	}
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/templates/"); // Ruta base de las plantillas
		resolver.setSuffix(".html"); // Extensión de las plantillas
		return resolver;
	}
}
