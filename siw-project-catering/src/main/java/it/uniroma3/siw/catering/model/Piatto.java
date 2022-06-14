package it.uniroma3.siw.catering.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Piatto {

	/* ******************* */
	/* VARIABILI D'ISTANZA */
	/* ******************* */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank private String nome;
	
	@NotBlank private String descrizione;
	
	@ManyToMany(mappedBy = "piatti")
	private List<Buffet> buffet;
	
	@ManyToMany
	private List<Ingrediente> ingredienti;
	
	/* *********** */
	/* COSTRUTTORI */
	/* *********** */
	
	public Piatto() {}
	
	public Piatto(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.ingredienti = new ArrayList<Ingrediente>();
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
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public void setListaBuffet(List<Buffet> listaBuffet) {
		this.buffet = listaBuffet;
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

	public String getDescrizione() {
		return this.descrizione;
	}

	public List<Ingrediente> getIngredienti() {
		return this.ingredienti;
	}

	public List<Buffet> getListaBuffet() {
		return buffet;
	}
}
