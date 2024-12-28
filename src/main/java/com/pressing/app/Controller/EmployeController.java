package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pressing.app.Entity.Employe;
import com.pressing.app.Repository.EmployeRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;
    private String message = "";
    private Employe employeEdit = new Employe();

    @GetMapping("/")
    public String getEmployes(Model model) {
        model.addAttribute("employes", employeRepository.findAll());
        message = "";
        return "/Employes/employes";
    }

    @GetMapping("/form")
    public String EmployesRegistry(Model model) {
        model.addAttribute("employe", employeEdit);
        employeEdit = new Employe();
        return "/Employes/employesForm";
    }

    @PostMapping("/add")
    public String addEmploye(@Valid Employe employe, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "/Employes/employesForm";
        }
        if (employe.getId() != null) {
            message = "Employé mis à jour avec succès";
            employeRepository.save(employe);
            model.addAttribute("message", message);
            model.addAttribute("employe", new Employe());
            return "/Employes/employesForm";
        }
        employeRepository.save(employe);
        message = "L'employé " + employe.getNom() + " ajouté avec succès";
        model.addAttribute("message", message);
        model.addAttribute("employe", new Employe());
        return "/Employes/employesForm";
    }

    @GetMapping("/edit/{id}")
    public String editEmploye(@PathVariable Long id, Model model) {
        Employe employe = employeRepository.findById(id).get();
        employeEdit = employe;
        return "redirect:/employes/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmploye(@PathVariable Long id, Model model) {
        employeRepository.deleteById(id);
        message = "l'employé supprimé avec succès";
        model.addAttribute("message", message);
        return "redirect:/employes/";
    }

}
