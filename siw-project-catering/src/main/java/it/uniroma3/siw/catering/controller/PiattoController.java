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

import it.uniroma3.siw.catering.controller.validator.PiattoValidator;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired private PiattoService piattoService;
	@Autowired private PiattoValidator piattoValidator;
	@Autowired private BuffetService buffetService;
	@Autowired private IngredienteService ingredienteService;
	
	/* ********************* */
	/* OPERAZIONI LATO ADMIN */
	/* ********************* */
	
	@GetMapping("/admin/piattoForm")
	public String toPiattoForm(Model model) {
		model.addAttribute(new Piatto());
		return "/admin/form/piattoForm.html";
	}
	
	@PostMapping("/admin/piatto")
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		this.piattoValidator.validate(piatto, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.piattoService.save(piatto);
			return "/admin/addSuccesso/inserimentoPiatto.html";
		}
		
		return "/admin/form/piattoForm.html";
	}
	
	@GetMapping("/admin/elencoPiatti")
	public String getElencoPiattiAdmin(Model model) {
		List<Piatto> elencoPiatti = this.piattoService.findAllPiatti();
		model.addAttribute("elencoPiatti", elencoPiatti);
		
		return "/admin/elenchi/elencoPiatti.html";
	}
	
	@GetMapping("/admin/piatto/{idPiatto}/{idBuffet}")
	public String getPiattoAdmin(@PathVariable("idPiatto") Long idPiatto, @PathVariable("idBuffet") Long idBuffet, Model model) {
		Piatto piatto = this.piattoService.findById(idPiatto);
		model.addAttribute("piatto", piatto);
		Buffet buffet = this.buffetService.findById(idBuffet);
		model.addAttribute("buffet", buffet);
	
		List<Ingrediente> tuttiIngredienti = this.ingredienteService.findAllIngredienti();
		List<Ingrediente> elencoIngredienti = new ArrayList<>();
		
		for(Ingrediente i: tuttiIngredienti) {
			if(i.getListaPiatti().contains(piatto))
				elencoIngredienti.add(i);
		}
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		
		return "/admin/visualizza/piatto.html";
	}
	
	@GetMapping("/admin/piatto/{idPiatto}")
	public String getPiattoAdmin(@PathVariable("idPiatto") Long idPiatto, Model model) {
		Piatto piatto = this.piattoService.findById(idPiatto);
		model.addAttribute("piatto", piatto);
		
		List<Ingrediente> tuttiIngredienti = this.ingredienteService.findAllIngredienti();
		List<Ingrediente> elencoIngredienti = new ArrayList<>();
		
		for(Ingrediente i: tuttiIngredienti) {
			if(i.getListaPiatti().contains(piatto))
				elencoIngredienti.add(i);
		}
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		
		return "/admin/visualizza/piatto.html";
	}
	
	@GetMapping("/admin/cancellaPiatto/{id}")
	public String toCancellaPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = this.piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "/admin/cancella/cancellaPiatto.html";
	}
	
	@GetMapping("/admin/confermaCancellazionePiatto/{id}")
	public String confermaCancellazionePiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = this.piattoService.findById(id);
		
		List<Ingrediente> elencoIngredienti = piatto.getIngredienti();
		for (Ingrediente i: elencoIngredienti)
			i.getListaPiatti().remove(piatto);
		
		this.piattoService.delete(piatto);
		
		return this.getElencoPiattiAdmin(model);
	}
	
	@GetMapping("/admin/inserisciIngrediente/{idPiatto}")
	public String toInserisciIngrediente(@PathVariable("idPiatto") Long id, Model model) {
		Piatto piatto = this.piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		
		List<Ingrediente> tuttiIngredienti = this.ingredienteService.findAllIngredienti();
		List<Ingrediente> elencoIngredienti = new ArrayList<>();
		
		for(Ingrediente i: tuttiIngredienti) {
			if (!i.getListaPiatti().contains(piatto))
				elencoIngredienti.add(i);
		}
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		
		model.addAttribute("ingrediente", new Ingrediente());
		
		return "/admin/inserisci/inserisciIngredientePerPiatto.html";
	}
	
	@PostMapping("/admin/inserimentoIngrediente/{piattoId}")
	public String inserimentoIngrediente(@PathVariable("piattoId") Long id, @ModelAttribute("ingrediente") Ingrediente ingrediente, Model model) {
		Piatto piatto = this.piattoService.findById(id);
		piatto.getIngredienti().add(ingrediente);
		
		this.piattoService.save(piatto);
		
		return "/admin/inserisci/inserisciIngredientePerPiattoConSuccesso.html";
	}
	
	@GetMapping("/admin/rimuoviIngrediente/{ingredienteId}/{piattoId}")
	public String rimuoviIngrediente(@PathVariable("ingredienteId") Long ingredienteId, @PathVariable("piattoId") Long piattoId, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(ingredienteId);
		Piatto piatto = this.piattoService.findById(piattoId);
		
		piatto.getIngredienti().remove(ingrediente);
		
		this.piattoService.save(piatto);
		
		return "/admin/rimuovi/rimuoviIngredientePerPiattoConSuccesso.html";
	}
	
	/* ******************** */
	/* OPERAZIONI LATO USER */
	/* ******************** */
	
	@GetMapping("/user/piatto/{idPiatto}/{idBuffet}")
	public String getPiattoUser(@PathVariable("idPiatto") Long idPiatto, @PathVariable("idBuffet") Long idBuffet, Model model) {
		Piatto piatto = this.piattoService.findById(idPiatto);
		model.addAttribute("piatto", piatto);
		Buffet buffet = this.buffetService.findById(idBuffet);
		model.addAttribute("buffet", buffet);
		
		List<Ingrediente> tuttiIngredienti = this.ingredienteService.findAllIngredienti();
		List<Ingrediente> elencoIngredienti = new ArrayList<>();
		
		for(Ingrediente i: tuttiIngredienti) {
			if(i.getListaPiatti().contains(piatto))
				elencoIngredienti.add(i);
		}
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		
		return "/user/visualizza/piatto.html";
	}
	
}
