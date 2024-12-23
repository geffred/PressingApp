package com.pressing.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pressing.app.Entity.Employe;
import com.pressing.app.Repository.EmployeRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("employes", employeRepository.findAll());
        return "/Employes/employes";
    }

    @PostMapping("/add")
    public String postMethodName(@Valid Employe employes, Model model) {
        employeRepository.save(employes);
        return "/Employes/employesForm";
    }

}
