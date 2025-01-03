package com.pressing.app.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pressing.app.Entity.Commande;
import com.pressing.app.Entity.Paiement;
import com.pressing.app.Repository.CommandeRepository;
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

    @Autowired
    private CommandeRepository commandeRepository;

    private Paiement paiementEdit = new Paiement();

    @GetMapping("/")
    public String getPaiement(Model model) {
        model.addAttribute("paiements", paiementRepository.findAll());
        model.addAttribute("commandes", commandeRepository.findAll());
        model.addAttribute("paiement", new Paiement());
        paiementEdit = new Paiement();
        return "Paiements/paiements";
    }

    @GetMapping("/filter")
    public String filterCommande(Model model,
            @RequestParam(name = "rechercher", required = false) String rechercher,
            @RequestParam(name = "payee", required = false) Boolean payee,
            @RequestParam(name = "dateDebut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(name = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        List<Paiement> paiementsFiltres = new ArrayList<>();

        if (rechercher != null && !rechercher.isEmpty()) {
            paiementsFiltres.addAll(paiementRepository.findByCommandeClientNomContaining(rechercher));
        }

        if (payee != null) {
            paiementsFiltres = paiementsFiltres.isEmpty()
                    ? paiementRepository.findByCommandePayee(payee)
                    : paiementsFiltres.stream()
                            .filter(paiement -> paiement.getCommande().isPayee() == payee)
                            .toList();
        }

        if (dateDebut != null && dateFin != null) {
            paiementsFiltres = paiementsFiltres.isEmpty()
                    ? paiementRepository.findByDatePaiementBetween(dateDebut, dateFin)
                    : paiementsFiltres.stream()
                            .filter(paiement -> !paiement.getDatePaiement().isBefore(dateDebut) &&
                                    !paiement.getDatePaiement().isAfter(dateFin))
                            .toList();
        }

        if (paiementsFiltres.isEmpty()) {
            paiementsFiltres = paiementRepository.findAll();
        }

        model.addAttribute("paiements", paiementsFiltres);
        return "Paiements/paiements";
    }

    @GetMapping("/form")
    public String paiementForm(Model model) {
        model.addAttribute("commandes", commandeRepository.findAll());
        model.addAttribute("paiement", paiementEdit);
        return "Paiements/paiementsForm";
    }

    @PostMapping("/add")
    public String addPaiement(Model model, @Valid Paiement paiement, Errors errors) {
        if (errors.hasErrors()) {
            return "Paiements/paiementsForm";
        }
        try {
            Commande c = commandeRepository.findById(paiement.getCommande().getId()).get();
            double montant = paiement.getCommande().getVetement().getQuantite()
                    * paiement.getCommande().getVetement().getCategorie().getPrixUnitaire();

            paiement.setMontant(montant);
            c.setPayee(true);
            commandeRepository.save(c);
            paiementRepository.save(paiement);
            message = "Paiement numéro " + paiement.getId() + " effectué avec succès.";
            model.addAttribute("paiement", new Paiement());
        } catch (Exception e) {
            message = "Cette Commande a déjà été payée!";
            System.err.println("Erreur lors de l'ajout du paiement : " + e.getMessage());
        }
        model.addAttribute("message", message);
        model.addAttribute("commandes", commandeRepository.findAll());
        return "Paiements/paiementsForm";
    }

    @GetMapping("/edit/{id}")
    public String editPaiement(@PathVariable Long id, Model model) {
        paiementEdit = paiementRepository.findById(id).get();
        model.addAttribute("paiement", paiementEdit);
        model.addAttribute("commandes", commandeRepository.findAll());
        return "Paiements/paiementsFormUpdate";
    }

    @GetMapping("/delete/{id}")
    public String deletePaiement(@PathVariable Long id, Model model) {
        paiementRepository.deleteById(id);
        message = "Paiement numéro " + id + " supprimé avec succès.";
        return "redirect:/paiements/";
    }
}
