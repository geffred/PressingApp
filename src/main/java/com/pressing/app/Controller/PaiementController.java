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

@Controller
@RequestMapping("/paiements")
public class PaiementController {

    private String message = "";
    @Autowired
    private PaiementRepository paiementRepository;

    @GetMapping("/")
    public String getPaiement(Model model) {
        model.addAttribute("paiements", paiementRepository.findAll());
        return "/Paiements/paiements";
    }

    @GetMapping("/add")
    public String addPaiement(Model model, @Valid Paiement paiement, Errors errors) {
        if (errors.hasErrors()) {
            return "/Paiements/paiementsForm";
        }
        paiementRepository.save(paiement);
        message = "Paiement numéro " + paiement.getId() + " ajouté avec succès";
        return "/Paiements/paiementsForm";
    }

    @GetMapping("/edit")
    public String editPaiement(Model model, @Valid Paiement paiement, Errors errors) {
        if (errors.hasErrors()) {
            return "/Paiements/paiementsForm";
        }
        paiementRepository.save(paiement);
        message = "Paiement numéro " + paiement.getId() + " a été mis à jour avec succès";
        return "/Paiements/paiementsForm";
    }

    @GetMapping("/delete/{id}")
    public String deletePaiement(Model model, Long id) {
        paiementRepository.deleteById(id);
        message = "Paiement numéro " + id + " supprimé avec succès";
        return "/Paiements/paiements";
    }

}
