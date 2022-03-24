package com.ahorcado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahorcado.dao.JuegoDAO;
import com.ahorcado.models.Juego;
import com.ahorcado.models.Usuario;

@Service
public class JuegoService {

	@Autowired
	JuegoDAO juegoDAO;
	
	
	public Juego save(Juego juego) {
		return juegoDAO.save(juego);
	}
	
	public Juego edit(Juego juego) {
		return juegoDAO.save(juego);
	}
	
	public List<Juego> findAll() {
		return juegoDAO.findAll();
	}

	public Juego findById(Long id) {
		return juegoDAO.findById(id).orElse(null);
	}
	
	public List<Juego> findByUsuario(Usuario usuario) {
		return juegoDAO.findByUsuario(usuario);
	}


	
}

