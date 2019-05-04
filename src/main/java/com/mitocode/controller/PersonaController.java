package com.mitocode.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
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

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Persona;
import com.mitocode.service.IPersonaService;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/Personas")
public class PersonaController {

	@Autowired
	private IPersonaService service;
	
	@GetMapping
	public ResponseEntity<List<Persona>> listar() {
		List<Persona> Personas = service.listar();
		return new ResponseEntity<List<Persona>>(Personas, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public Resource<Persona> listarPorId(@PathVariable("id") Integer idPersona) {
		Persona pac = service.leer(idPersona);
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idPersona);
		}
		
		Resource<Persona> resource = new Resource<Persona>(pac);
		// localhost:8080/Personas/{id}
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(idPersona));
		resource.add(linkTo.withRel("Persona-resource"));
		
		return resource;
	}
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Persona Personas) {
		Persona pac = service.registrar(Personas);
		// localhost:8080/Personas/{id}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getIdPersona()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Object> modificar(@Valid @RequestBody Persona Personas) {
		service.modificar(Personas);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer idPersona) {
		Persona pac = service.leer(idPersona);
		if (pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idPersona);
		} else {
			service.eliminar(idPersona);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
