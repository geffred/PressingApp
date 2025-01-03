package com.pressing.app.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pressing.app.Entity.Commande;
import com.pressing.app.Repository.ClientRepository;
import com.pressing.app.Repository.CommandeRepository;
import com.pressing.app.Repository.EmployeRepository;
import com.pressing.app.Repository.VetementRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

@RequestMapping("/commandes")
public class CommandeController {

    private String message = "";
    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VetementRepository vetementRepository;

    @Autowired
    private EmployeRepository employeRepository;

    private Commande commandeEdit = new Commande();

    @GetMapping("/")
    public String getCommande(Model model) {
        model.addAttribute("commandes", commandeRepository.findAll());
        model.addAttribute("message", message);
        commandeEdit = new Commande();
        message = "";
        return "/commandes/commandes";
    }

    @GetMapping("/filter")
    public String filterCommande(Model model,
            @RequestParam(name = "rechercher", required = false) String rechercher,
            @RequestParam(name = "payee", required = false) Boolean payee) {
        // Initialisation de la liste pour stocker les commandes filtrées
        List<Commande> commandesRecherche = new ArrayList<>();

        // Si le client a spécifié un nom à rechercher
        if (rechercher != null && !rechercher.isEmpty()) {
            commandesRecherche.addAll(commandeRepository.findByClientNomContaining(rechercher));
        }

        // Si le client a spécifié un statut "payée"
        if (payee != null) {
            // Si une recherche par nom a déjà été effectuée, on filtre les résultats
            // existants
            if (!commandesRecherche.isEmpty()) {
                commandesRecherche = commandesRecherche.stream()
                        .filter(commande -> commande.isPayee() == payee)
                        .toList();
            } else {
                commandesRecherche.addAll(commandeRepository.findByPayee(payee));
            }
        }

        // Si aucune recherche n'a été effectuée, retourner toutes les commandes
        if (commandesRecherche.isEmpty()) {
            commandesRecherche = commandeRepository.findAll();
        }

        // Ajouter les commandes filtrées au modèle
        model.addAttribute("commandes", commandesRecherche);

        // Retourner la vue des commandes
        return "/commandes/commandes";
    }

    @GetMapping("/form")
    public String commandeForm(Model model) {
        model.addAttribute("commande", new Commande());
        model.addAttribute("commande", commandeEdit);
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("vetements", vetementRepository.findAll());
        model.addAttribute("employes", employeRepository.findAll());

        return "/commandes/commandesForm";
    }

    @PostMapping("/add")
    public String addCommande(Model model, @Valid Commande commande, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("vetements", vetementRepository.findAll());
            model.addAttribute("employes", employeRepository.findAll());
            return "/commandes/commandesForm";

        }
        if (commande.getDateCommande().isAfter(commande.getDateLivraison())) {
            message = "La date de livraison doit être postérieure à la date de commande";
            model.addAttribute("message", message);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("vetements", vetementRepository.findAll());
            model.addAttribute("employes", employeRepository.findAll());
            return "/commandes/commandesForm";

        }

        message = "Commande ajoutée avec succès";
        commandeRepository.save(commande);
        model.addAttribute("message", message);
        model.addAttribute("commande", new Commande());
        return "/commandes/commandesForm";
    }

    @PostMapping("/update")
    public String updateCommande(Model model, @Valid Commande commande, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("vetements", vetementRepository.findAll());
            model.addAttribute("employes", employeRepository.findAll());
            return "/commandes/commandesFormUpdate";

        }

        if (commande.getDateCommande().isAfter(commande.getDateLivraison())) {
            message = "La date de livraison doit être postérieure à la date de commande";
            model.addAttribute("message", message);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("vetements", vetementRepository.findAll());
            model.addAttribute("employes", employeRepository.findAll());
            return "/commandes/commandesForm";

        }

        message = "Commande numéro " + commande.getId() + " modifiée avec succès";
        commandeRepository.save(commande);
        model.addAttribute("message", message);
        model.addAttribute("commande", new Commande());
        return "/commandes/commandesFormUpdate";
    }

    @GetMapping("/edit/{id}")
    public String editCommande(Model model, @PathVariable Long id) {
        commandeEdit = commandeRepository.findById(id).get();
        model.addAttribute("commande", commandeEdit);
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("vetements", vetementRepository.findAll());
        model.addAttribute("employes", employeRepository.findAll());
        return "/commandes/commandesFormUpdate";
    }

    @GetMapping("/delete/{id}")
    public String deleteCommande(@PathVariable Long id, Model model) {
        try {
            commandeRepository.deleteById(id);
            message = "Commande numéro " + id + " supprimée avec succès";
        } catch (Exception e) {
            message = "Impossible de supprimer la commande numéro " + id;
        }
        return "redirect:/commandes/";
    }

}
