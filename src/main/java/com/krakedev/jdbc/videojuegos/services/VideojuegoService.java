package com.krakedev.jdbc.videojuegos.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.krakedev.jdbc.videojuegos.VideojuegoJdbc;
import com.krakedev.videojuegos.entidades.Videojuego;

@Service
public class VideojuegoService {
	public boolean insertar(Videojuego v) {
		return VideojuegoJdbc.insertar(v);
	}
	
	public Videojuego buscar(String codigo) {
		return VideojuegoJdbc.buscar(codigo);
	}
	
	public ArrayList<Videojuego> listar(){
		return VideojuegoJdbc.listar();
	}
	
	public boolean actualizar(String codigo, Videojuego v) {
		return VideojuegoJdbc.actualizar(codigo, v);
	}
	
	public boolean eliminar(String codigo) {
		return VideojuegoJdbc.eliminar(codigo);
	}
}
