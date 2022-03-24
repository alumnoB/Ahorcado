package com.ahorcado.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahorcado.models.Juego;
import com.ahorcado.models.Usuario;

@Repository
public interface JuegoDAO extends JpaRepository<Juego, Long> {
	
	Optional<Juego> findById(Long id);
	List<Juego> findByUsuario(Usuario usuario);
	
	
}
