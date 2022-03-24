package com.ahorcado.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.apache.commons.lang3.StringUtils;


@Entity
public class Juego {
	
	@Id @GeneratedValue
	private Long id;
	
	private String palabra;
	
	private String formateoPalabra;
	
	private Integer vidas;
	
	@ElementCollection
	private List<String> letrasUsadas;
	
	@OneToMany
	private List<Partida> partida;
	
	@CreationTimestamp
	private LocalDateTime startDate;
	
	@ManyToOne
	private Usuario usuario;
	
	private boolean terminado;
	

	public Juego() {}

	public Juego(String palabra, Usuario usuario) {
		super();
		this.palabra = palabra;
		this.formateoPalabra = formatWord(palabra);
		this.usuario = usuario;
		vidas = 5;
		terminado = false;
		letrasUsadas = new ArrayList<>();
		partida = new ArrayList<>();
	}

	
	private String formatWord(String word) {
		return StringUtils.stripAccents(word).toUpperCase();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public String getFormateoPalabra() {
		return formateoPalabra;
	}

	public void setFormateoPalabra(String formateoPalabra) {
		this.formateoPalabra = formateoPalabra;
	}

	public Integer getVidas() {
		return vidas;
	}

	public void setVidas(Integer vidas) {
		this.vidas = vidas;
	}

	public List<String> getLetrasUsadas() {
		return letrasUsadas;
	}

	public void setLetrasUsadas(List<String> letrasUsadas) {
		this.letrasUsadas = letrasUsadas;
	}

	public List<Partida> getPartida() {
		return partida;
	}

	public void setPartida(List<Partida> partida) {
		this.partida = partida;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}


}
