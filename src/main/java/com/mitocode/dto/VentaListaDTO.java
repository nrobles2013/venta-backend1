package com.mitocode.dto;

import java.util.List;

import com.mitocode.model.Venta;
import com.mitocode.model.Producto;

public class VentaListaDTO {

	private Venta venta;
	private List<Producto> listProd;
	
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	public List<Producto> getListProd() {
		return listProd;
	}
	public void setListProd(List<Producto> listProd) {
		this.listProd = listProd;
	}

}
