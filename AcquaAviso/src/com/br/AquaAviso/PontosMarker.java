package com.br.AquaAviso;

import com.google.android.gms.maps.model.LatLng;

public class PontosMarker {
	LatLng ponto;
	String local;
	String estado;
	public PontosMarker(LatLng l, String local2, String estado2) {
		ponto=l;
		local=local2;
		estado=estado2;
		// TODO Auto-generated constructor stub
	}
	public LatLng getPonto() {
		return ponto;
	}
	public void setPonto(LatLng ponto) {
		this.ponto = ponto;
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

}
