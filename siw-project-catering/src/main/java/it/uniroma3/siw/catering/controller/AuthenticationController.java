package it.uniroma3.siw.catering.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.CredentialsValidator;
import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.model.User;
import it.uniroma3.siw.catering.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired private CredentialsService credentialsService;
	@Autowired private CredentialsValidator credentialsValidator;
	
	@GetMapping("/register")
	public String toPaginaRegistrazione(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegistrazione.html";
	}
	
	@PostMapping("/register")
	public String registraUtente(@Valid @ModelAttribute("user") User user,
								BindingResult userBindingResults,
								@Valid @ModelAttribute("credentials") Credentials credentials,
								BindingResult credentialsBindingResults,
								Model model)
	{
		this.credentialsValidator.validate(credentials, credentialsBindingResults);
		if (!credentialsBindingResults.hasErrors() && !userBindingResults.hasErrors()) {
			credentials.setUser(user);
			this.credentialsService.save(credentials);
			return "index.html";
			
		}
		return "formRegistrazione.html";
	}
	
	@GetMapping("/login")
	public String toPaginaLogin(Model model) {
		return "formLogin.html";
	}
	
	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
    	
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE))
            return "admin/home.html";
        if (credentials.getRole().equals(Credentials.DEFAULT_ROLE))
        	return "user/home.html";
        else
        	return "loginForm.html";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "index.html";
	}
	
	@GetMapping("/index")
	public String getIndex() {
		return "index.html";
	}
	
}
