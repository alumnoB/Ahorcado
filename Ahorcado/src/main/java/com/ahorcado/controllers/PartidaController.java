package com.ahorcado.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahorcado.services.JuegoService;
import com.ahorcado.services.PartidaService;
import com.ahorcado.services.UsuarioService;
import com.ahorcado.archivo.StorageService;
import com.ahorcado.models.Juego;
import com.ahorcado.models.Partida;


@Controller
public class PartidaController extends HomeController {
	
	 private int contador;
	 private String contadorVisitas = "";
	 
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JuegoService juegoService;
	
	@Autowired
	private PartidaService partidaService;
	
	@Autowired
	@Qualifier("global")
	private StorageService storageService;

	@Autowired
	@Qualifier("user")
	private StorageService storageUserService;
	
	private Juego currentGame;

	@ModelAttribute("current_game")
	public Juego getCurrentGame() {
		
		return new Juego("", getCurrentUser());
	}
	
	@PostConstruct
	public void init() {
		
	}
	
	
	@GetMapping("/game/new/{mode}/{id}")
	public String newGame(Model model, @PathVariable long mode, @PathVariable long id) {
		
		if (usuarioService.findById(id) == null)
			return "redirect:/";
		
		String word = null;
		if (mode == 1)
			word = storageService.getRandomWord();
		else if (mode == 2) {
			if (storageUserService.getWords().isEmpty())
				return "redirect:/index";
			word = storageUserService.getRandomWord();
		}
		Juego game = new Juego(word, getCurrentUser());
		juegoService.save(game);
		
		return "redirect:/game/"+game.getId();
	}
	
	
	
	@GetMapping("/game/edit/{id}")
	public String editGame(Model model, @PathVariable long id) {
		if (juegoService.findById(id) == null)
			return "redirect:/";
		
		return "redirect:/game/"+id;
	}
	

	@GetMapping("/game/{id}")
	public String initGame(HttpServletRequest sol, Model model, @PathVariable long id, @RequestParam(name="q", required=false) String query) throws UnknownHostException {
		Juego game = juegoService.findById(id);
		if (game == null || game.isTerminado())
			return "redirect:/index";
		
		//Si no es nula ni está vacía la consulta, empieza el proceso de validación
		if (query != null && !query.trim().isEmpty()) {
			String letter = query.toUpperCase();
			
			//Si aun no ha sido usada, y el caracter es una letra alfabetica...
			if (!game.getLetrasUsadas().contains(letter) && (letter.charAt(0) >= 'A' && letter.charAt(0) <='Z')) {	
				if (!game.getFormateoPalabra().contains(letter)) {
					boolean hasAttempts = loseAttempt(game);
					//Si se acabaron los intentos, fin del juego
					if (!hasAttempts)
						game.setTerminado(true);
				}	
				//Agregamos la letra al listado de letras usadas y generamos el objeto Play que contenga los datos de esta jugada
				game.getLetrasUsadas().add(letter);
				game.getPartida().add(generatePlay(game, letter));
				
				//Si ya acertó la palabra, fin del juego
				if (guessedWord(game))  
					game.setTerminado(true);
				//Guardamos cambios de la partida en la BBDD
				juegoService.edit(game);
			}
		}
		
		//Añadimos una pista adicional en caso de ganar la partida
		if (game.isTerminado() && game.getVidas() > 0) {
			
			usuarioService.edit(usuario);
		}
		
		currentGame = game;
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		String direccionIP = ip;
				
		model.addAttribute("direccionIP", direccionIP);
		
		model.addAttribute("current_game", game);
		
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
		return "game";
	}

	/**
	 * Resta un intento a la partida y devuelve un indicador de si todavía le quedan intentos
	 * @param game
	 * @return
	 */
	private boolean loseAttempt(Juego game) {
		if (game.getVidas() > 0)
			game.setVidas(game.getVidas()-1);
		return game.getVidas() > 0;
	}
	
	/**
	 * Devuelve un indicador de si ya se averiguó la palabra de la partida, comprobando si todas las letras
	 * de la palabra están contenidas en la lista de letras usadas.
	 * @param game
	 * @return
	 */
	private boolean guessedWord(Juego game) {
		for (int i=0; i<game.getFormateoPalabra().length(); i++) {
			if (!game.getLetrasUsadas().contains(game.getFormateoPalabra().substring(i,i+1)))
				return false;
		}
	
		return true;
	}
	
	
	
	private Partida generatePlay(Juego game, String letter) {
		String word = "";
		
		for (int i=0; i < game.getFormateoPalabra().length(); i++) {
			if (game.getLetrasUsadas().contains(game.getFormateoPalabra().substring(i, i+1)))
				word += game.getPalabra().substring(i, i+1);
			else 
				word += "_";
		}
		
		Partida play = new Partida(game, word, letter, game.getVidas());
		partidaService.save(play);
		return play;
	}

}
