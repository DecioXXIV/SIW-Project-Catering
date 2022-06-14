package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Buffet buffet) {
		this.buffetRepository.save(buffet);
	}

	public boolean alreadyExists(Buffet buffet) {
		return this.buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
	}

	public List<Buffet> findAllBuffet() {
		List<Buffet> elencoBuffet = new ArrayList<>();
		for (Buffet b: this.buffetRepository.findAll())
			elencoBuffet.add(b);
		return elencoBuffet;
	}

	public Buffet findById(Long id) {
		return this.buffetRepository.findById(id).get();
	}

	@Transactional
	public void delete(Buffet buffet) {
		this.buffetRepository.delete(buffet);
	}

}
