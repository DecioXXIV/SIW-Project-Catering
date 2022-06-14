package it.uniroma3.siw.catering.model;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Buffet {

	/* ******************* */
	/* VARIABILI D'ISTANZA */
	/* ******************* */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank private String nome;
	
	private String descrizione;
	
	@ManyToOne 
	private Chef chef;
	
	@ManyToMany
	private List<Piatto> piatti;
	
	/* *********** */
	/* COSTRUTTORI */
	/* *********** */
	
	public Buffet() {}
	
	public Buffet(String nome, String descrizione, Chef chef) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.piatti = new ArrayList<Piatto>();
		this.chef = chef;
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
	
	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	
	public void setChef(Chef chef) {
		this.chef = chef;
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

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public Chef getChef() {
		return this.chef;
	}

	/* ****************** */
	/* METODI DI SUPPORTO */
	/* ****************** */
	
	@Override
	public boolean equals(Object obj) {
		Buffet that = (Buffet) obj;
		if(this.getId() != null && that.getId() != null)
			return this.getId().equals(that.getId());
		return this.getNome().equals(that.getNome());
	}
}
