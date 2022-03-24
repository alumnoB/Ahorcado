package com.ahorcado.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Partida {

	@Id @GeneratedValue
	private Long position;
	
	@ManyToOne
	private Juego juego;
	
	private String palabra;
	
	private String letra;
	
	private Integer vidas;
	
	
	public Partida() {}


	public Partida( Juego juego, String palabra, String letra, Integer vidas) {
		super();
		this.juego = juego;
		this.palabra = palabra;
		this.letra = letra;
		this.vidas = vidas;
	}


	public Long getPosition() {
		return position;
	}


	public void setPosition(Long position) {
		this.position = position;
	}


	public Juego getJuego() {
		return juego;
	}


	public void setJuego(Juego juego) {
		this.juego = juego;
	}


	public String getPalabra() {
		return palabra;
	}


	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}


	public String getLetra() {
		return letra;
	}


	public void setLetra(String letra) {
		this.letra = letra;
	}


	public Integer getVidas() {
		return vidas;
	}


	public void setVidas(Integer vidas) {
		this.vidas = vidas;
	}
	
	

}
