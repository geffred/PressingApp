package com.pressing.app.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pressing.app.Entity.Client;
import com.pressing.app.Entity.Commande;
import com.pressing.app.Repository.ClientRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    private String message = "";
    private Client clientEdit = new Client();

    @GetMapping("/")
    public String getClients(Model model) {

        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("client", new Client());
        message = "";
        return "clients/clients";
    }

    @GetMapping("/filter")
    public String filterClient(Model model,
            @RequestParam(name = "rechercher", required = false) String rechercher) {
        // Initialisation de la liste pour stocker les clients filtrés
        List<Client> clientRecherche = new ArrayList<>();

        // Si le client a spécifié un critère de recherche
        if (rechercher != null && !rechercher.isEmpty()) {
            // Rechercher à la fois par nom et par email
            clientRecherche.addAll(clientRepository.findByNomContainingOrEmailContaining(rechercher, rechercher));
        }

        // Si aucune recherche n'a été effectuée, retourner tous les clients
        if (clientRecherche.isEmpty()) {
            clientRecherche = clientRepository.findAll();
        }

        // Ajouter les clients filtrés au modèle
        model.addAttribute("clients", clientRecherche);

        // Retourner la vue des clients
        return "/clients/clients";
    }

    @GetMapping("/form")
    public String getClientForm(Model model) {
        model.addAttribute("client", clientEdit);
        model.addAttribute("message", message);
        clientEdit = new Client();
        return "clients/clientsForm";
    }

    @RequestMapping("/add")
    public String addClient(Model model, @Valid Client client, Errors errors) {

        if (errors.hasErrors()) {
            return "/Clients/clientsForm";
        }

        try {

            clientRepository.save(client);

            if (client.getId() != null) {
                message = "Client mis à jour avec succès";
            } else {
                message = "Client " + client.getNom() + " ajouté avec succès";
            }
            model.addAttribute("message", message);
            model.addAttribute("client", new Client());
            clientEdit = new Client();
        } catch (Exception e) {
            message = "Erreur lors de l'ajout du client l'adresse email ou le numéro de téléphone est déjà utilisé";
            model.addAttribute("message", message);
            model.addAttribute("client", client);
        }
        return "/Clients/clientsForm";
    }

    @GetMapping("/edit/{id}")
    public String editClient(Model model, @PathVariable Long id) {
        Client client = clientRepository.findById(id).get();
        model.addAttribute("client", client);
        clientEdit = client;
        return "redirect:/clients/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, Model model) {
        clientRepository.deleteById(id);
        message = "Client supprimé avec succès";
        model.addAttribute("message", message);
        return "redirect:/clients/";
    }

}
