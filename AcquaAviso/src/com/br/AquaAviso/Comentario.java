package com.br.AquaAviso;

public class Comentario {
	private String nome, email, tipo, local, estado, relato, id, idUsuario,curtida,ncurtida,data;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getRelato() {
		return relato;
	}
	public void setRelato(String relato) {
		this.relato = relato;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getCurtida() {
		return curtida;
	}
	public void setCurtida(String curtida) {
		this.curtida = curtida;
	}
	public String getNcurtida() {
		return ncurtida;
	}
	public void setNcurtida(String ncurtida) {
		this.ncurtida = ncurtida;
	}
	public String toString(){
		return "ID do comentario: "+id+"\nID do usuario: "+idUsuario+"\nnome: "+nome+"\nemail: "+email+"\ntipo: "+tipo+"\nlocal: "+local+"\nEstado: "+estado+"\nRelato:\n"+relato+"\ncurtidas: "+curtida+"\nnão curtidas: "+ncurtida+"\nData: "+data;
	}
	
}
