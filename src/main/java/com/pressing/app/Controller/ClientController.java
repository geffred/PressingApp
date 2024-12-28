package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pressing.app.Entity.Client;
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
        if (client.getId() != null) {
            message = "Client mis à jour avec succès";
        } else {
            message = "Client " + client.getNom() + " ajouté avec succès";
        }

        clientRepository.save(client);
        model.addAttribute("message", message);
        model.addAttribute("client", new Client());
        clientEdit = new Client();
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
