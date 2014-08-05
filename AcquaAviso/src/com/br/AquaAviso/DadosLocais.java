package com.br.AquaAviso;

import com.google.android.gms.maps.model.LatLng;

public class DadosLocais {

	private String local;
	
	private String estado;
	private LatLng coordenada;
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
	public LatLng getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(LatLng coodenada) {
		this.coordenada = coodenada;
	}
	
}
