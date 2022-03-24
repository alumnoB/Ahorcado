package com.ahorcado.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ahorcado.models.Juego;
import com.ahorcado.services.JuegoService;

@Controller
public class JuegoController extends HomeController {
	
	private int contador;
	private String contadorVisitas = "";
	 
	@Autowired
	private JuegoService juegoService;

	
	@ModelAttribute("mygames")
	public List<Juego> misJuegos() {
		return juegoService.findByUsuario(getCurrentUser());
	}
	
	@ModelAttribute("resume_game")
	public Juego juego() {
		
		return new Juego( "", getCurrentUser());
	}
	

	@GetMapping({"/", "/index"})
	public String index(RedirectAttributes redirect, Model model, HttpServletRequest sol) throws UnknownHostException {
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		String direccionIP = ip;
				
		model.addAttribute("direccionIP", direccionIP);
		
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
		return "index";
	}
	
	@GetMapping("/game/resume/{id}")
	public String resumeGame(Model model,  HttpServletRequest sol, @PathVariable long id) throws UnknownHostException {
		Juego game = juegoService.findById(id);
		if (game == null)
			return "redirect:/index";
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		String direccionIP = ip;
				
		model.addAttribute("direccionIP", direccionIP);
		model.addAttribute("resume_game", game);
		
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
		
		return "resume";
	}
	
}
