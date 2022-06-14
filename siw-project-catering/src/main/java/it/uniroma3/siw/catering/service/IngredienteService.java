package it.uniroma3.siw.catering.service;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired private IngredienteRepository ingredienteRepository;
	
	@Transactional
	public void save(Ingrediente ingrediente) {
		this.ingredienteRepository.save(ingrediente);
	}

	public List<Ingrediente> findAllIngredienti() {
		java.util.List<Ingrediente> elencoIngredienti = new ArrayList<>();
		for (Ingrediente i: this.ingredienteRepository.findAll())
			elencoIngredienti.add(i);
		return elencoIngredienti;
	}

	public boolean alreadyExists(Ingrediente ingrediente) {
		return this.ingredienteRepository.existsByNomeAndOrigineAndDescrizione(ingrediente.getNome(), ingrediente.getOrigine(), ingrediente.getDescrizione());
	}

	public Ingrediente findById(Long id) {
		return this.ingredienteRepository.findById(id).get();
	}

	@Transactional
	public void delete(Ingrediente ingrediente) {
		this.ingredienteRepository.delete(ingrediente);
	}

}
