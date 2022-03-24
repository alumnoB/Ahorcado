package com.ahorcado.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahorcado.models.Juego;
import com.ahorcado.models.Partida;

@Repository
public interface PartidaDAO extends JpaRepository<Partida, Long> {
	List<Partida> findByJuego(Juego juego);
}
