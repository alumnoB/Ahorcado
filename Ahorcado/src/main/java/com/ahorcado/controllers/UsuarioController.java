package com.ahorcado.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ahorcado.models.Usuario;
import com.ahorcado.services.UsuarioService;


@Controller
public class UsuarioController {
	
	 private int contador;
	 private String contadorVisitas = "";
	 
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/auth/login")
	public String login(Model model, HttpServletRequest sol) throws UnknownHostException {
		
		String ip = InetAddress.getLocalHost().getHostAddress();
				
		String direccionIP = ip;
				
		model.addAttribute("direccionIP", direccionIP);
		
		model.addAttribute("user", new Usuario());
		
		if (sol.getCookies() == null) {

            contador = 1;

            contadorVisitas = String.valueOf(contador);

          
            model.addAttribute("bienvenida","Bienvenido por primera vez");

        } else {


           
            contador++;
            contadorVisitas = String.valueOf(contador);

          
            model.addAttribute("bienvenida","Bienvenido de nuevo");

        }
		
		model.addAttribute("contador",contadorVisitas);
		
		System.out.print(direccionIP);
		return "login";
	}
	
	@GetMapping("/auth/register")
	public String registrar(RedirectAttributes redirect, Model model, HttpServletRequest solicitudHttp) throws UnknownHostException{
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		String direccionIP = ip;
				
		model.addAttribute("direccionIP", direccionIP);
		return "registro";
	}
	
	@PostMapping("/auth/save")
	public String register(String nombre, String password,RedirectAttributes redirect, Model model, HttpServletRequest solicitudHttp) throws UnknownHostException {
		
		if(usuarioService.existsByNombre(nombre)) {
			model.addAttribute("usuarioRepetido", "El usuario ya existe");
			return "registro";
		}
		
		if(nombre == "" || password == "") {
			model.addAttribute("camposVacios", "Los campos no pueden estar vacíos");
			return "registro";
		}
		
	

		String ip = solicitudHttp.getRemoteAddr();
	
		String direccionIP = ip;

		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setPassword(passwordEncoder.encode(password));
		
		
		usuarioService.save(usuario);
		
		model.addAttribute("direccionIP", direccionIP);
		redirect.addFlashAttribute("usuarioRegistrado", "Registro completado, inicie sesión");
		
		return "redirect:/auth/login";
	}
	
}
