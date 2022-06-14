package it.uniroma3.siw.catering.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired 
	protected CredentialsRepository credentialsRepository;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Transactional
	public void save(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);
//		credentials.setRole(Credentials.ADMIN_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		this.credentialsRepository.save(credentials);
	}
	
	public boolean alreadyExists(Credentials credentials) {
		return this.credentialsRepository.existsByUsername(credentials.getUsername());
	}
	
	@Transactional
	public Credentials getCredentials(String username) {
		return this.credentialsRepository.findByUsername(username).get();
	}
	
}
