package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pressing.app.Entity.Vetement;
import com.pressing.app.Repository.CategorieRepository;
import com.pressing.app.Repository.VetementRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/vetements")
public class VetementController {

    private String message = "";
    private Vetement vetementEdit = new Vetement();

    @Autowired
    private VetementRepository vetementRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/")
    public String getVetements(Model model) {
        model.addAttribute("vetements", vetementRepository.findAll());
        model.addAttribute("message", message);
        message = "";
        return "/vetements/vetements";
    }

    @GetMapping("/form")
    public String vetementsForm(Model model) {
        message = "";
        vetementEdit = new Vetement();
        model.addAttribute("vetement", vetementEdit);
        model.addAttribute("categories", categorieRepository.findAll());

        return "/vetements/vetementsForm";
    }

    @PostMapping("/add")
    public String addVetement(@Valid Vetement vetement, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "/vetements/vetementsForm";
        }

        message = "vêtement ajouté avec succès";
        vetementRepository.save(vetement);
        model.addAttribute("message", message);
        model.addAttribute("vetement", vetementEdit);
        model.addAttribute("categories", categorieRepository.findAll());
        return "/vetements/vetementsForm";
    }

    @PostMapping("/update")
    public String updateVetement(@Valid Vetement vetement, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("categories", categorieRepository.findAll());
            return "/vetements/vetementsFormUpdate";
        }
        message = "vêtement numéro " + vetement.getId() + " modifié avec succès";
        vetementRepository.save(vetement);
        model.addAttribute("message", message);
        model.addAttribute("vetement", vetementEdit);
        model.addAttribute("categories", categorieRepository.findAll());
        return "/vetements/vetementsFormUpdate";
    }

    @GetMapping("edit/{id}")
    public String editFormVetement(@PathVariable Long id, Model model) {
        vetementEdit = vetementRepository.findById(id).get();
        model.addAttribute("vetement", vetementEdit);
        model.addAttribute("categories", categorieRepository.findAll());
        return "/vetements/vetementsFormUpdate";
    }

    @GetMapping("/delete/{id}")
    public String deleteVetement(@PathVariable Long id, Model model) {
        try {
            vetementRepository.deleteById(id);
            message = "vêtement numéro " + id + " supprimé avec succès";
        } catch (Exception e) {
            message = "Erreur lors de la suppression du vêtement numéro " + id;
        }
        return "redirect:/vetements/";
    }
}
