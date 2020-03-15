package com.usuario.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.usuario.api.service.ImplementacaoUserDetailsService;

/*Mapeaia URL, enderecos, autoriza ou bloqueia acessoa a URL*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	
	/*Configura as solicitaÃ§Ãµes de acesso por Http*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*Ativando a proteÃ§Ã£o contra usuÃ¡rio que nÃ£o estÃ£o validados por TOKEN*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		/*Ativando a permissÃ£o para acesso a pÃ¡gina incial do sistema EX: sistema.com.br/index*/
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		/*URL de Logout - Redireciona apÃ³s o user deslogar do sistema*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*Maperia URL de Logout e insvalida o usuÃ¡rio*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*Filtra requisiÃ§Ãµes de login para autenticaÃ§Ã£o*/
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
									UsernamePasswordAuthenticationFilter.class)
		
		/*Filtra demais requisiÃ§Ãµes paa verificar a presenÃ§Ã£o do TOKEN JWT no HEADER HTTP*/
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	/*Service que irÃ¡ consultar o usuÃ¡rio no banco de dados*/	
	auth.userDetailsService(implementacaoUserDetailsService)
	
	/*PadrÃ£o de codigiÃ§Ã£o de senha*/
	.passwordEncoder(new BCryptPasswordEncoder());
	
	}

}