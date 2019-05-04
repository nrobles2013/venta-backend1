package com.mitocode.service;


import com.mitocode.dto.VentaListaDTO;
import com.mitocode.model.Venta;

public interface IVentaService extends ICRUD<Venta>{
	Venta registrarTransaccional(VentaListaDTO VentaDTO);
}
