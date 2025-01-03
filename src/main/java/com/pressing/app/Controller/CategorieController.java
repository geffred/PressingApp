package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pressing.app.Entity.Categorie;
import com.pressing.app.Repository.CategorieRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;
    private String message = "";
    private Categorie categorieEdit = new Categorie();

    @GetMapping("/")
    public String getCategories(Model model) {
        model.addAttribute("categories", categorieRepository.findAll());
        model.addAttribute("message", message);
        message = "";
        return "/Categories/categories";
    }

    @GetMapping("/form")
    public String categoriesRegistry(Model model) {
        model.addAttribute("categorie", categorieEdit);
        categorieEdit = new Categorie();
        return "/Categories/categoriesForm";
    }

    @PostMapping("/add")
    public String addCategorie(@Valid Categorie categorie, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "/Categories/categoriesForm";
        }
        try {
            if (categorie.getId() != null) {
                message = "Catégorie " + categorie.getNom() + " modifiée avec succès";
            } else {
                message = "Catégorie " + categorie.getNom() + " ajoutée avec succès";
            }
            categorieRepository.save(categorie);
            model.addAttribute("message", message);
            model.addAttribute("categorie", new Categorie());
        } catch (Exception e) {
            message = "Erreur lors de l'ajout de la catégorie , cette catégorie existe déjà";
            model.addAttribute("message", message);
        }
        return "/Categories/categoriesForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategorie(@PathVariable Long id, Model model) {
        try {
            categorieRepository.deleteById(id);
            message = "La catégorie supprimée avec succès";
        } catch (Exception e) {
            message = "Erreur lors de la suppression de la catégorie , cette catégorie est utilisée dans une commande";
        }
        return "redirect:/categories/";
    }

    @GetMapping("/edit/{id}")
    public String editCategorie(@PathVariable Long id, Model model) {
        categorieEdit = categorieRepository.findById(id).get();
        return "redirect:/categories/form";

    }

}
