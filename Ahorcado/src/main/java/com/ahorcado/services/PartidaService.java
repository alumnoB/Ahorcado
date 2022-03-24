package com.ahorcado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahorcado.dao.PartidaDAO;
import com.ahorcado.models.Juego;
import com.ahorcado.models.Partida;

@Service
public class PartidaService {

	@Autowired
	PartidaDAO partidaDAO;
	
	
	public Partida save(Partida partida) {
		return partidaDAO.save(partida);
	}
	
	public Partida edit(Partida partida) {
		return partidaDAO.save(partida);
	}
	
	public List<Partida> findAll() {
		return partidaDAO.findAll();
	}
	
	public List<Partida> findByJuego(Juego juego) {
		return partidaDAO.findByJuego(juego);
	}
	
}

