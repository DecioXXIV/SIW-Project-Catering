package it.uniroma3.siw.catering.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Ingrediente {

	/* ******************* */
	/* VARIABILI D'ISTANZA */
	/* ******************* */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank private String nome;
	private String origine;
	private String descrizione;
	
	@ManyToMany(mappedBy = "ingredienti")
	private List<Piatto> piatti;
	
	/* *********** */
	/* COSTRUTTORI */
	/* *********** */
	
	public Ingrediente() {}
	
	public Ingrediente(String nome, String origine, String descrizione) {
		this.nome = nome;
		this.origine = origine;
		this.descrizione = descrizione;
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
	
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public void setListaPiatti(List<Piatto> listaPiatti) {
		this.piatti = listaPiatti;
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

	public String getOrigine() {
		return this.origine;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public List<Piatto> getListaPiatti() {
		return piatti;
	}
}
