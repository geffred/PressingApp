package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pressing.app.Entity.Vetement;
import com.pressing.app.Repository.VetementRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/vetements")
public class VetementController {

    private String message = "";

    @Autowired
    private VetementRepository vetementRepository;

    @RequestMapping("/")
    public String getVetements(Model model) {
        message = "";
        model.addAttribute("vetements", vetementRepository.findAll());
        return "/vetements/vetements";
    }

    @RequestMapping("/add")
    public String addVetement(@Valid Vetement vetement, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return "/vetements/vetementsForm";
        }
        vetementRepository.save(vetement);
        message = "Vetement numéro " + vetement.getId() + " ajouté avec succès";
        return "/vetements/vetementsForm";
    }

    @RequestMapping("/edit")
    public String editVetement(@Valid Vetement vetement, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return "/vetements/vetementsForm";
        }
        vetementRepository.save(vetement);
        message = "Vetement numéro " + vetement.getId() + " a été mis à jour avec succès";
        return "/vetements/vetementsForm";
    }

    @RequestMapping("/delete/{id}")
    public String deleteVetement(Long id, Model model) {
        vetementRepository.deleteById(id);
        message = "Vetement numéro " + id + " supprimé avec succès";
        return "/vetements/vetements";
    }
}
