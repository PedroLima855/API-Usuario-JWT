package com.usuario.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableTransactionManagement
@EnableAutoConfiguration
@RestController
@SpringBootApplication
public class UsuarioApiApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioApiApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
	/*Mapeamento Global que refletem em todo o sistema*/
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/usuario/**")
		.allowedMethods("*")
		.allowedOrigins("*");
		/*Liberando o mapeamento de usuario para todas as origens*/
		
	}
	

}
