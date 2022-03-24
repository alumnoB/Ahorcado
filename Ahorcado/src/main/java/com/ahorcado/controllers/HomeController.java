package com.ahorcado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ahorcado.models.Usuario;
import com.ahorcado.services.UsuarioService;

@Controller
@Primary
public class HomeController {

	@Autowired
	UsuarioService usuarioService;
	
	protected Usuario usuario;
	
	@ModelAttribute("current_user")
	public Usuario getCurrentUser() {
		String nombre = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioService.findByNombre(nombre);
		return usuario;
	}
	
}
