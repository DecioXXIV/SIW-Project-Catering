package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired private ChefRepository chefRepository;
	
	@Transactional
	public void save(Chef chef) {
		this.chefRepository.save(chef);
	}
	
	public Chef findByNome(Chef chef) {
		return this.chefRepository.findByNome(chef.getNome());
	}

	public boolean alreadyExists(Chef chef) {
		return this.chefRepository.existsByNome(chef.getNome());
	}

	public List<Chef> findAllChef() {
		List<Chef> elencoChef = new ArrayList<>();
		
		for (Chef c: this.chefRepository.findAll())
			elencoChef.add(c);
		
		return elencoChef;
	}

	public Chef findById(Long id) {
		return this.chefRepository.findById(id).get();
	}

	@Transactional
	public void delete(Chef chef) {
		this.chefRepository.delete(chef);	
	}

	
	
}
