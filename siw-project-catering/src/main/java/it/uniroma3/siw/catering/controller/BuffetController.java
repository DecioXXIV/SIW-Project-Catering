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

import it.uniroma3.siw.catering.controller.validator.BuffetValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class BuffetController {

	@Autowired private BuffetService buffetService;
	@Autowired private BuffetValidator buffetValidator;
	@Autowired private PiattoService piattoService;
	
	/* ********************* */
	/* OPERAZIONI LATO ADMIN */
	/* ********************* */
	
	@GetMapping("/admin/buffetForm")
	public String toBuffetForm(Model model) {
		model.addAttribute(new Buffet());
		return "/admin/form/buffetForm.html";
	}
	
	@PostMapping("/admin/buffet")
	public String addBuffet(@Valid @ModelAttribute ("buffet") Buffet buffet, BindingResult bindingResult, Model model) {
		this.buffetValidator.validate(buffet, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.buffetService.save(buffet);
			return "/admin/addSuccesso/inserimentoBuffet.html";
		}
		
		return "/admin/form/buffetForm.html";
	}
	
	@GetMapping("/admin/elencoBuffet")
	public String getElencoBuffetAdmin(Model model) {
		List<Buffet> elencoBuffet = this.buffetService.findAllBuffet();
		model.addAttribute("elencoBuffet", elencoBuffet);
		return "/admin/elenchi/elencoBuffet.html";
	}
	
	@GetMapping("/admin/cancellaBuffet/{id}")
	public String toCancellaBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "/admin/cancella/cancellaBuffet.html";
	}
	
	@GetMapping("/admin/confermaCancellazioneBuffet/{id}")
	public String confermaCancellazioneBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		this.buffetService.delete(buffet);
		
		return this.getElencoBuffetAdmin(model);
	}
	
	@GetMapping("/admin/buffet/{id}")
	public String getBuffetAdmin(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		Chef chef = buffet.getChef();
		model.addAttribute("chef", chef);
		List<Piatto> elencoPiatti = buffet.getPiatti();
		model.addAttribute("elencoPiatti", elencoPiatti);
		
		return "/admin/visualizza/buffet.html";
	}
	
	@GetMapping("/admin/inserisciPiatto/{idBuffet}")
	public String toInserisciPiatto(@PathVariable("idBuffet") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		
		List<Piatto> tuttiPiatti = this.piattoService.findAllPiatti();
		List<Piatto> elencoPiatti = new ArrayList<>();
		for (Piatto p: tuttiPiatti) {
			if (!p.getListaBuffet().contains(buffet))
				elencoPiatti.add(p);
		}
		model.addAttribute("elencoPiatti", elencoPiatti);
		
		model.addAttribute("piatto", new Piatto());
		
		return "/admin/inserisci/inserisciPiattoPerBuffet.html";
	}
	
	@PostMapping("/admin/inserimentoPiatto/{buffetId}")
	public String inserimentoPiatto(@PathVariable("buffetId") Long id, @ModelAttribute("piatto") Piatto piatto, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		buffet.getPiatti().add(piatto);
		
		this.buffetService.save(buffet);
		
		return "/admin/inserisci/inserisciPiattoPerBuffetConSuccesso.html";
	}
	
	@GetMapping("/admin/rimuoviPiatto/{piattoId}/{buffetId}")
	public String rimuoviPiatto(@PathVariable("piattoId") Long piattoId, @PathVariable("buffetId") Long buffetId, Model model) {
		Piatto piatto = this.piattoService.findById(piattoId);
		Buffet buffet = this.buffetService.findById(buffetId);
		
		buffet.getPiatti().remove(piatto);
		
		this.buffetService.save(buffet);
		
		return "/admin/rimuovi/rimuoviBuffetPerChefConSuccesso.html";
		
	}
	
	/* ******************** */
	/* OPERAZIONI LATO USER */
	/* ******************** */
	
	@GetMapping("/user/elencoBuffet")
	public String getElencoBuffetUser(Model model) {
		List<Buffet> tuttiBuffet = this.buffetService.findAllBuffet();
		List<Buffet> elencoBuffet = new ArrayList<>();
		
		for (Buffet b: tuttiBuffet) {
			if (b.getChef() != null)
				elencoBuffet.add(b);
		}
		
		model.addAttribute("elencoBuffet", elencoBuffet);
		
		return "/user/elenchi/elencoBuffet.html";
	}
	
	@GetMapping("/user/buffet/{id}")
	public String getBuffetUser(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		Chef chef = buffet.getChef();
		model.addAttribute("chef", chef);
		List<Piatto> elencoPiatti = buffet.getPiatti();
		model.addAttribute("elencoPiatti", elencoPiatti);
		
		return "/user/visualizza/buffet.html";
	}
}
