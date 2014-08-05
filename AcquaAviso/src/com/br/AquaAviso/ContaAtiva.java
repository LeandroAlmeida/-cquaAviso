package com.br.AquaAviso;

public class ContaAtiva {
	private String nome, email, senha, id;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String toString(){
		return "id: "+this.id+" nome: "+this.nome+" e-mail: "+this.email+" senha: "+this.senha;
	}
}
