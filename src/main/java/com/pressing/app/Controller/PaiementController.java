package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pressing.app.Entity.Paiement;
import com.pressing.app.Repository.PaiementRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/paiements")
public class PaiementController {

    private String message = "";
    @Autowired
    private PaiementRepository paiementRepository;
    private Paiement paiementEdit = new Paiement();

    @GetMapping("/")
    public String getPaiement(Model model) {
        model.addAttribute("paiements", paiementRepository.findAll());
        return "Paiements/paiements";
    }

    @GetMapping("/form")
    public String paiementForm(Model model) {
        model.addAttribute("paiement", paiementEdit);
        return "Paiements/paiementsForm";
    }

    @PostMapping("/add")
    public String addPaiement(Model model, @Valid Paiement paiement, Errors errors) {
        if (errors.hasErrors()) {
            return "/Paiements/paiementsForm";
        }
        if (paiement.getId() != null) {
            message = "Paiement numéro " + paiement.getId() + " modifié avec succès";
        } else {
            message = "Paiement  ajouté avec succès";
        }
        paiementRepository.save(paiement);
        model.addAttribute("message", message);
        model.addAttribute("paiement", new Paiement());
        return "/Paiements/paiementsForm";
    }

    @GetMapping("/edit/{id}")
    public String editPaiement(@PathVariable Long id, Model model) {
        paiementEdit = paiementRepository.findById(id).get();
        return "redirect:/paiements/form";
    }

    @GetMapping("/delete/{id}")
    public String deletePaiement(Model model, Long id) {
        paiementRepository.deleteById(id);
        message = "Paiement numéro " + id + " supprimé avec succès";
        return "redirect:/paiements/";
    }

}
