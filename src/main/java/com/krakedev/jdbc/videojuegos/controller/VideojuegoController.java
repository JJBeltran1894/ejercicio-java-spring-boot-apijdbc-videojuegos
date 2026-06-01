package com.krakedev.jdbc.videojuegos.controller;

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

import com.krakedev.apijdbc.excepciones.ErrorResponse;
import com.krakedev.apijdbc.excepciones.VideojuegoDuplicadoExcepcion;
import com.krakedev.apijdbc.excepciones.VideojuegoNoEncontradoExcepcion;
import com.krakedev.jdbc.videojuegos.services.VideojuegoService;
import com.krakedev.videojuegos.entidades.Videojuego;

@RestController
@RequestMapping("/videojuegos")
public class VideojuegoController {
	
	private final VideojuegoService servicio;
	
	public VideojuegoController(VideojuegoService servicio ) {
		this.servicio = servicio;
	}
	
	@PostMapping
	public ResponseEntity<?> insertar(@RequestBody Videojuego v) {
		try {
			return ResponseEntity.ok(servicio.insertar(v)) ;
		}catch (VideojuegoDuplicadoExcepcion ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.CONFLICT.value());
			return new ResponseEntity <>(er,HttpStatus.CONFLICT );
		}catch (RuntimeException ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity <>(er,HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscar(@PathVariable String codigo) {
		try {
			return ResponseEntity.ok(servicio.buscar(codigo)) ;
		}catch (VideojuegoNoEncontradoExcepcion ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value());
			return new ResponseEntity <>(er,HttpStatus.NOT_FOUND );
		}catch (RuntimeException ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity <>(er,HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
	
	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			return ResponseEntity.ok(servicio.listar()) ;
		}catch (VideojuegoNoEncontradoExcepcion ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value());
			return new ResponseEntity <>(er,HttpStatus.NOT_FOUND );
		}catch (RuntimeException ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity <>(er,HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<?> actualizar(@PathVariable String codigo, @RequestBody Videojuego v) {
		try {
			return ResponseEntity.ok(servicio.actualizar(codigo, v)) ;
		}catch (VideojuegoNoEncontradoExcepcion ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value());
			return new ResponseEntity <>(er,HttpStatus.NOT_FOUND );
		}catch (RuntimeException ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity <>(er,HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> eliminar(@PathVariable String codigo) {
		try {
			return ResponseEntity.ok(servicio.eliminar(codigo)) ;
		}catch (VideojuegoNoEncontradoExcepcion ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value());
			return new ResponseEntity <>(er,HttpStatus.NOT_FOUND );
		}catch (RuntimeException ex) {
			ErrorResponse er = new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity <>(er,HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
	
	
	
	

}
