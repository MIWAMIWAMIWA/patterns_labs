package com.example.patternslabs.controllers;

import com.example.patternslabs.models.Dentist;
import com.example.patternslabs.services.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dentists")
public class DentistController {

    @Autowired
    private DentistService dentistService;

    @GetMapping
    public String listDentists(Model model) {
        List<Dentist> dentists = dentistService.getAllDentists();
        model.addAttribute("dentists", dentists);
        return "dentists";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("dentist", new Dentist());
        return "add-dentist";
    }

    @PostMapping("/add")
    public String addDentist(@ModelAttribute Dentist dentist) {
        dentistService.createDentist(dentist);
        return "redirect:/dentists";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Dentist dentist = dentistService.getDentist(id);
        model.addAttribute("dentist", dentist);
        return "edit-dentist";
    }

    @PostMapping("/edit/{id}")
    public String editDentist(@PathVariable int id, @ModelAttribute Dentist dentist) {
        dentist.setId(id);
        dentistService.updateDentist(dentist);
        return "redirect:/dentists";
    }

    @GetMapping("/delete/{id}")
    public String deleteDentist(@PathVariable int id) {
        dentistService.deleteDentist(id);
        return "redirect:/dentists";
    }
}

