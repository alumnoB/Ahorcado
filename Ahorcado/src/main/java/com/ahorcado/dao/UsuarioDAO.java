package com.ahorcado.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahorcado.models.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario,Long> {
	Optional<Usuario> findByNombreIgnoreCase(String nombre);
	Optional<Usuario> findById(Long id);
	boolean existsByNombre(String nombre);
}
