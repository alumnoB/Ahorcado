package com.ahorcado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ahorcado.dao.UsuarioDAO;
import com.ahorcado.models.Usuario;

@Service
public class UsuarioService {

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public Usuario save(Usuario  usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		return usuarioDAO.save(usuario);
	}
	
	public Usuario edit(Usuario usuario) {
		return usuarioDAO.save(usuario);
	}
	
	public Usuario findByNombre(String nombre) {
		return usuarioDAO.findByNombreIgnoreCase(nombre).orElse(null);
	}
	
	public Usuario findById(Long id) {
		return usuarioDAO.findById(id).orElse(null);
	}
	
	public boolean existsByNombre(String nombre) {
		return usuarioDAO.existsByNombre(nombre);
	}
}
