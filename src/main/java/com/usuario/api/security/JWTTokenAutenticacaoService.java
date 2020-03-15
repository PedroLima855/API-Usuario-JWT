package com.usuario.api.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.usuario.api.ApplicationContextLoad;
import com.usuario.api.model.Usuario;
import com.usuario.api.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	
	/*Tem de validade do Token 2 dias*/
	private static final long EXPIRATION_TIME = 172800000;
	
	/*Uma senha unica para compor a autenticacao e ajudar na seguranÃ§a*/
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	/*Prefixo padrÃ£o de Token*/
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/*Gerando token de autenticado e adiconando ao cabeÃ§alho e resposta Http*/
	public void addAuthentication(HttpServletResponse response , String username) throws IOException {
		
		/*Montagem do Token*/
		String JWT = Jwts.builder() /*Chama o gerador de Token*/
				        .setSubject(username) /*Adicona o usuario*/
				        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo de expiraÃ§Ã£o*/
				        .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*CompactaÃ§Ã£o e algoritmos de geraÃ§Ã£o de senha*/
		
		/*Junta token com o prefixo*/
		String token = TOKEN_PREFIX + " " + JWT; /*Bearer 87878we8we787w8e78w78e78w7e87w*/
		
		/*Adiciona no cabeÃ§alho http*/
		response.addHeader(HEADER_STRING, token); /*Authorization: Bearer 87878we8we787w8e78w78e78w7e87w*/
		
		/*Escreve token como responsta no corpo http*/
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
	
	
	/*Retorna o usuÃ¡rio validado com token ou caso nÃ£o sejÃ¡ valido retorna null*/
	public Authentication getAuhentication(HttpServletRequest request) {
		
		/*Pega o token enviado no cabeÃ§alho http*/
		
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			
			/*Faz a validaÃ§Ã£o do token do usuÃ¡rio na requisiÃ§Ã£o*/
			String user = Jwts.parser().setSigningKey(SECRET) /*Bearer 87878we8we787w8e78w78e78w7e87w*/
								.parseClaimsJws(token.replace(TOKEN_PREFIX, "")) /*87878we8we787w8e78w78e78w7e87w*/
								.getBody().getSubject(); /*JoÃ£o Silva*/
			if (user != null) {
				
				Usuario usuario = ApplicationContextLoad.getApplicationContext()
						        .getBean(UsuarioRepository.class).findUserByLogin(user);
				
				if (usuario != null) {
					
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(), 
							usuario.getSenha(),
							usuario.getAuthorities());
					
				}
			}
			
		}
	
		return null; /*NÃ£o autorizado*/
		
	}
	

}

