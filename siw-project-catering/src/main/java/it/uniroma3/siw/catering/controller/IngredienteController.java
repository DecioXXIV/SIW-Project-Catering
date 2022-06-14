package it.uniroma3.siw.catering.controller;

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

import it.uniroma3.siw.catering.controller.validator.IngredienteValidator;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;

@Controller
public class IngredienteController {

	@Autowired private IngredienteValidator ingredienteValidator;
	@Autowired private IngredienteService ingredienteService;
	@Autowired private PiattoService piattoService;
	

	/* ********************* */
	/* OPERAZIONI LATO ADMIN */
	/* ********************* */
	
	@GetMapping("/admin/ingredienteForm")
	public String toIngredienteForm(Model model) {
		model.addAttribute(new Ingrediente());
		return "/admin/form/ingredienteForm.html";
	}
	
	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		this.ingredienteValidator.validate(ingrediente, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.ingredienteService.save(ingrediente);
			return "/admin/addSuccesso/inserimentoIngrediente.html";
		}
		
		return "/admin/form/ingredienteForm.html";
	}
	
	@GetMapping("/admin/elencoIngredienti")
	public String getElencoIngredientiAdmin(Model model) {
		List<Ingrediente> elencoIngredienti = this.ingredienteService.findAllIngredienti();
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		
		return "/admin/elenchi/elencoIngredienti.html";
	}
	
	@GetMapping("/admin/cancellaIngrediente/{id}")
	public String toCancellaIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "/admin/cancella/cancellaIngrediente.html";
	}
	
	@GetMapping("/admin/confermaCancellazioneIngrediente/{id}")
	public String confermaCancellazioneIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		this.ingredienteService.delete(ingrediente);
		
		return this.getElencoIngredientiAdmin(model);
	}
	
	@GetMapping("/admin/ingrediente/{idIngrediente}/{idPiatto}")
	public String getIngredienteAdmin(@PathVariable("idIngrediente") Long idIngrediente, @PathVariable("idPiatto") Long idPiatto, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(idIngrediente);
		model.addAttribute("ingrediente", ingrediente);
		Piatto piatto = this.piattoService.findById(idPiatto);
		model.addAttribute("piatto", piatto);
		
		return "/admin/visualizza/ingrediente.html";
	}
	
	@GetMapping("/admin/ingrediente/{idIngrediente}")
	public String getIngredienteAdmin(@PathVariable("idIngrediente") Long idIngrediente, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(idIngrediente);
		model.addAttribute("ingrediente", ingrediente);
		
		return "/admin/visualizza/ingrediente.html";
	}
	
	/* ******************** */
	/* OPERAZIONI LATO USER */
	/* ******************** */
	
	@GetMapping("/user/ingrediente/{idIngrediente}/{idPiatto}")
	public String getIngredienteUser(@PathVariable("idIngrediente") Long idIngrediente, @PathVariable("idPiatto") Long idPiatto, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(idIngrediente);
		model.addAttribute("ingrediente", ingrediente);
		Piatto piatto = this.piattoService.findById(idPiatto);
		model.addAttribute("piatto", piatto);
		
		return "/user/visualizza/ingrediente.html";
	}
}
