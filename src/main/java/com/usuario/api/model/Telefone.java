package com.usuario.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Telefone {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)// Gera os IDs auto no banco
	private Long id;
	
	private String numero;
	
	@JsonIgnore// Evita um loop do retorno dos registro
	@org.hibernate.annotations.ForeignKey(name = "usuario_id")//Tabela cm chave estrangeira
	
	// Muitos telefones para um usuario
	@ManyToOne(optional = false)// só e possivel cadastrar telefone se cadastrar usuario
	
	// Relacionamento entre as classes
	private Usuario usuario;

	// Getts e Setts
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
		
	

}

