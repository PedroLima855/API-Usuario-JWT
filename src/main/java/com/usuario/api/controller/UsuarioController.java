package com.usuario.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.api.model.Usuario;
import com.usuario.api.repository.UsuarioRepository;
import com.usuario.api.service.ImplementacaoUserDetailsService;

@CrossOrigin // Permite o acesso das requisições de todas as origens  
@RestController
@RequestMapping (value = "usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	// Consulta pelo ID
	@GetMapping (value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> buscar(@PathVariable (value = "id")Long id) {
		
		Optional<Usuario> listar = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(listar.get(), HttpStatus.OK);
	}
	
	// Consulta todos 
	@GetMapping (value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> bucarTodos() {
		
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
		
	}
	
	// salva um registro
	@PostMapping (value = "/", produces = "application/json")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		
		// esse for percorre a lista e amarra aos usuarios
		for (int pos = 0; pos < usuario.getTelefones().size(); pos ++ ) {
			
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		// Criptografa a senha
		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		Usuario salvar = usuarioRepository.save(usuario);	
		
		return new ResponseEntity<Usuario>(salvar, HttpStatus.CREATED);
		
	}
	
	// Edita um registro
	@PutMapping (value = "/", produces = "application/json")
	public ResponseEntity<Usuario> editar(@RequestBody Usuario usuario) {
		
		// esse for percorre a lista e amarra aos usuarios
		for (int pos = 0; pos < usuario.getTelefones().size(); pos ++ ) {
					
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		Usuario userTemporario = usuarioRepository.findById(usuario.getId()).get();
		
		// se a senha for diferente criptografa e salva
		if (!userTemporario.getSenha().equals(usuario.getSenha())) { 
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}
		
		Usuario editarUsuario = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(editarUsuario, HttpStatus.OK);
	}
	
	// Deleta um registro
	@DeleteMapping (value = "/{id}", produces = "application/text")
	public String deletar(@PathVariable (value = "id")Long id) {
		
		usuarioRepository.deleteById(id);
		
		return "ok";
		
	}
	
}
