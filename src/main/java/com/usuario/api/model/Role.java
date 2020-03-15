package com.usuario.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table (name = "role") // Nome da tabela
@SequenceGenerator (name = "seq_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1)// Cria a tebala no banco de forma sequencial a partir da alocação 1
public class Role implements GrantedAuthority {

	// Determina a versão da class
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "seq_role")
	private Long id;
	
	private String nomeRole; // Papel*, papeis do tipo, ROLE GERENTE, ROLE SUPERVISOR

	// metodo que processa a autorização 
	@Override
	public String getAuthority() {
		
		return this.nomeRole;// retorna a autorização ou acesso, exp: role gerente, role supervisor
	}

	
	// Getts e Setts
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	
}
