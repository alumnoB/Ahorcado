package com.ahorcado.controllers;

import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahorcado.archivo.StorageService;
import com.ahorcado.models.CustomStringList;


@Controller
public class PalabraController extends HomeController {

	@SuppressWarnings("unused")
	private List<String> tempWords;
	
	@Autowired
	@Qualifier("user")
	private StorageService storageUserService;
	
	@PostConstruct
	public void init() {
		System.out.println("INIT EJECUTADO");
		storageUserService.setUser(true);
	}
	
	@ModelAttribute("userwords")
	public List<String> getWords() {
		return storageUserService.getWords();
	}
	
	@GetMapping("/wordconfiguration")
	public String wordconfiguration() {
		tempWords = new CustomStringList();
		return "wordconfiguration";
	}
	

	@GetMapping("/wordconfiguration/delete/{word}")
	public String deleteWord(@PathVariable String word) {
		storageUserService.removeWord(word);
		return "redirect:/wordconfiguration";
	}
	
	@PostMapping("/wordconfiguration/edit")
	public @ResponseBody Boolean editWord(@RequestParam("original") String original, @RequestParam("new") String newWord) {
		if (original.equalsIgnoreCase(newWord))
			return false;
		return storageUserService.updateWord(original, newWord);
	}
	
}
