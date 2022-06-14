package it.uniroma3.siw.catering.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Chef {
	
	/* ******************* */
	/* VARIABILI D'ISTANZA */
	/* ******************* */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank private String nome;
	
	@NotBlank private String cognome;
	
	@NotBlank private String nazionalita;
	
	@OneToMany(mappedBy = "chef", cascade = {CascadeType.PERSIST})
	private List<Buffet> buffet;
	
	/* *********** */
	/* COSTRUTTORI */
	/* *********** */
	
	public Chef() {}
	
	public Chef(String nome, String cognome, String nazionalita) {
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.buffet = new ArrayList<Buffet>();
	}

	/* ************* */
	/* METODI SETTER */
	/* ************* */
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public void setBuffet(List<Buffet> buffet) {
		this.buffet = buffet;
	}
	
	/* ************* */
	/* METODI GETTER */
	/* ************* */
	
	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public String getNazionalita() {
		return this.nazionalita;
	}

	public List<Buffet> getBuffet() {
		return this.buffet;
	}

}
