package com.mitocode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Producto;
import com.mitocode.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private IProductoService service;
	
	@GetMapping
	public List<Producto> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public Producto listarPorId(@PathVariable("id") Integer idProducto) {
		return service.leer(idProducto);
	}
	
	@PostMapping
	public Producto registrar(@RequestBody Producto med) {
		return service.registrar(med);
	}
	
	@PutMapping
	public Producto modificar(@RequestBody Producto med) {
		return service.modificar(med);
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable("id") Integer idProducto) {
		service.eliminar(idProducto);
	}
}
