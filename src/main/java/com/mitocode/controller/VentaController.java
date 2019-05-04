package com.mitocode.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.VentaDTO;
import com.mitocode.dto.VentaListaDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Venta;
import com.mitocode.service.IVentaService;

@RestController
@RequestMapping("/Ventas")
public class VentaController {

	@Autowired
	private IVentaService service;
	
	@GetMapping
	public ResponseEntity<List<Venta>> listar() {
		List<Venta> Ventas = service.listar();
		return new ResponseEntity<List<Venta>>(Ventas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VentaDTO> listarHateoas() {
		List<Venta> Ventas = new ArrayList<>();
		List<VentaDTO> VentasDTO = new ArrayList<>();
		Ventas = service.listar();
		
		for(Venta c : Ventas) {
			VentaDTO d = new VentaDTO();
			d.setIdVenta(c.getIdVenta());
			d.setPersona(c.getPersona());
			
			
			// localhost:8080/Ventas/1
			ControllerLinkBuilder linkTo = linkTo(methodOn(VentaController.class).listarPorId((c.getIdVenta())));
			d.add(linkTo.withSelfRel());
			VentasDTO.add(d);
			
			// pacientes/2
			ControllerLinkBuilder linkTo1 = linkTo(methodOn(PersonaController.class).listarPorId((c.getPersona().getIdPersona())));
			d.add(linkTo1.withSelfRel());
			VentasDTO.add(d);
			
		}
		
		return VentasDTO;
	}
	
	@GetMapping("/{id}")
	public Resource<Venta> listarPorId(@PathVariable("id") Integer idVenta) {
		Venta pac = service.leer(idVenta);
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idVenta);
		}
		
		Resource<Venta> resource = new Resource<Venta>(pac);
		// localhost:8080/Ventas/{id}
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(idVenta));
		resource.add(linkTo.withRel("Venta-resource"));
		
		return resource;
	}
	
	
	/*@PostMapping(produces = "application/json", consumes = "application/json")
	public Venta registrar(@RequestBody Venta Venta) {
		return service.registrar(Venta);
	}*/

	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> registrar(@Valid @RequestBody VentaListaDTO ventaDTO) {
		Venta pac = service.registrarTransaccional(ventaDTO);
		// localhost:8080/Ventas/{id}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getIdVenta()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Object> modificar(@Valid @RequestBody Venta Venta) {
		service.modificar(Venta);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer idVenta) {
		Venta pac = service.leer(idVenta);
		if (pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idVenta);
		} else {
			service.eliminar(idVenta);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
