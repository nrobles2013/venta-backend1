package com.mitocode.dto;

import org.springframework.hateoas.ResourceSupport;

import com.mitocode.model.Persona;

public class VentaDTO extends ResourceSupport {

	private int idVenta;
	private Persona persona;
	
	public int getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	

}
