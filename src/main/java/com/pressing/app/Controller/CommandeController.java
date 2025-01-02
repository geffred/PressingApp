package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

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
        message = "";
        return "/commandes/commandes";
    }

    @GetMapping("/form")
    public String commandeForm(Model model) {
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
        if (commande.getId() != null) {
            message = "Commande numéro " + commande.getId() + " modifiée avec succès";

        } else {
            message = "Commande ajoutée avec succès";
        }

        commandeRepository.save(commande);
        model.addAttribute("message", message);
        model.addAttribute("commande", new Commande());
        return "/commandes/commandesForm";
    }

    @GetMapping("/edit/{id}")
    public String editCommande(Model model, @PathVariable Long id) {
        commandeEdit = commandeRepository.findById(id).get();
        return "redirect:/commandes/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCommande(@PathVariable Long id, Model model) {
        commandeRepository.deleteById(id);
        message = "Commande numéro " + id + " supprimée avec succès";
        model.addAttribute("message", message);
        return "redirect:/commandes/";
    }

}
