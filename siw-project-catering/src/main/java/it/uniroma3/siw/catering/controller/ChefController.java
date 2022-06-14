package it.uniroma3.siw.catering.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.controller.validator.ChefValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;

@Controller
public class ChefController {

	@Autowired private ChefService chefService;
	@Autowired private ChefValidator chefValidator;
	@Autowired private BuffetService buffetService;
	
	/* ********************* */
	/* OPERAZIONI LATO ADMIN */
	/* ********************* */
	
	/* Metodo che porta al Form per inserire un Nuovo Chef
	 * La Pagina è inizializzata con uno "Chef vuoto" */
	@GetMapping("/admin/chefForm")
	public String toChefForm(Model model) {
		model.addAttribute(new Chef());
		return "/admin/form/chefForm.html";
	}
	
	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute ("chef") Chef chef, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			this.chefService.save(chef);
			return "/admin/addSuccesso/inserimentoChef.html";
		}
		
		return "/admin/form/chefForm.html";
	}
	
	@GetMapping("/admin/elencoChef")
	public String getElencoChefAdmin(Model model) {
		List<Chef> elencoChef = this.chefService.findAllChef();
		model.addAttribute("elencoChef", elencoChef);
		
		return "/admin/elenchi/elencoChef.html";
	}
	
	@GetMapping("/admin/chef/{id}")
	public String getChefAdmin(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		model.addAttribute("chef", chef);
		List<Buffet> elencoBuffet = chef.getBuffet();
		model.addAttribute("elencoBuffet", elencoBuffet);
		
		return "/admin/visualizza/chef.html";
	}
	
	@GetMapping("/admin/cancellaChef/{id}")
	public String toCancellaChef(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		model.addAttribute("chef", chef);
		return "/admin/cancella/cancellaChef.html";
	}
	
	@GetMapping("/admin/confermaCancellazioneChef/{id}")
	public String confermaCancellazioneChef(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		
		for(Buffet b: chef.getBuffet())
			b.setChef(null);
		this.chefService.delete(chef);
		
		return this.getElencoChefAdmin(model);
	}
	
	@GetMapping("/admin/inserisciBuffet/{idChef}")
	public String toInserisciBuffet(@PathVariable("idChef") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		model.addAttribute("chef", chef);
		
		List<Buffet> tuttiBuffet = this.buffetService.findAllBuffet();
		List<Buffet> elencoBuffet = new ArrayList<>();
		for(Buffet b: tuttiBuffet) {
			if(b.getChef() == null)
				elencoBuffet.add(b);
		}
		model.addAttribute("elencoBuffet", elencoBuffet);
		model.addAttribute("buffet", new Buffet());
		
		return "/admin/inserisci/inserisciBuffetPerChef.html";
	}
	
	@PostMapping("/admin/inserimentoBuffet/{chefId}")
	public String inserimentoBuffet(@PathVariable("chefId") Long id, @ModelAttribute("buffet") Buffet buffet, Model model) {
		Chef chef = this.chefService.findById(id);
		buffet.setChef(chef);
		this.buffetService.save(buffet);
				
		return "/admin/inserisci/inserisciBuffetPerChefConSuccesso.html";
	}
	
	@GetMapping("/admin/rimuoviBuffet/{buffetId}")
	public String rimuoviBuffet(@PathVariable("buffetId") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		Chef chef = buffet.getChef();
		buffet.setChef(null);
		this.buffetService.save(buffet);
		return "/admin/rimuovi/rimuoviBuffetPerChefConSuccesso.html";
	}
	
	/* ******************** */
	/* OPERAZIONI LATO USER */
	/* ******************** */
	
	@GetMapping("/user/elencoChef")
	public String getElencoChefUser(Model model) {
		List<Chef> elencoChef = this.chefService.findAllChef();
		model.addAttribute("elencoChef", elencoChef);
		
		return "/user/elenchi/elencoChef.html";
	}
	
	@GetMapping("/user/chef/{id}")
	public String getChefUser(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		model.addAttribute("chef", chef);
		List<Buffet> elencoBuffet = chef.getBuffet();
		model.addAttribute("elencoBuffet", elencoBuffet);
		
		return "/user/visualizza/chef.html";
	}
}
