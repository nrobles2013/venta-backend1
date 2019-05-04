package com.mitocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.VentaListaDTO;
import com.mitocode.model.Venta;
import com.mitocode.repo.IVentaRepo;
import com.mitocode.service.IVentaService;

@Service
public class VentaServiceImpl implements IVentaService{

	@Autowired
	private IVentaRepo repo;
	
	

	@Transactional
	@Override
	public Venta registrarTransaccional(VentaListaDTO ventaDTO) {
		//aqui insertando consulta + detalle_consulta
		ventaDTO.getVenta().getVentaDetalle().forEach(det -> det.setVenta(ventaDTO.getVenta()));
		repo.save(ventaDTO.getVenta());
		
		
		return ventaDTO.getVenta();
	}
	
	@Override
	public Venta registrar(Venta cons) {	
		/*for(DetalleConsulta det : cons.getDetalleConsulta()) {
			det.setConsulta(cons);
		}*/
		cons.getVentaDetalle().forEach(det -> det.setVenta(cons));
		return repo.save(cons);
	}


	@Override
	public Venta modificar(Venta t) {		
		return repo.save(t);
	}

	@Override
	public Venta leer(Integer id) {		
		return repo.findOne(id);
	}

	@Override
	public List<Venta> listar() {
		return repo.findAll();
	}

	@Override
	public void eliminar(Integer id) {
		repo.delete(id);
	}



}
