package com.pressing.app.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pressing.app.Entity.Commande;
import com.pressing.app.Repository.CommandeRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

@RequestMapping("/commandes")
public class CommandeController {

    private String message = "";
    private CommandeRepository commandeRepository;

    @GetMapping("/")
    public String getCommande(Model model) {
        model.addAttribute("commandes", commandeRepository.findAll());
        message = "";
        return "/commandes/commandes";
    }

    @GetMapping("/add")
    public String addCommande(Model model, @Valid Commande commande, Errors errors) {
        if (errors.hasErrors()) {
            return "/commandes/commandesForm";

        }
        commandeRepository.save(commande);
        message = "Commande numéro " + commande.getId() + " ajoutée avec succès";
        return "/commandes/commandesForm";
    }

    @GetMapping("/edit")
    public String editCommande(Model model, @Valid Commande commande, Errors errors) {
        if (errors.hasErrors()) {
            return "/commandes/commandesForm";
        }
        commandeRepository.save(commande);
        message = "Commande numéro " + commande.getId() + " modifiée avec succès";
        return "/commandes/commandesForm";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCommande(Long id, Model model) {
        commandeRepository.deleteById(id);
        message = "Commande numéro " + id + " supprimée avec succès";
        return "/commandes/commandes";
    }

}
