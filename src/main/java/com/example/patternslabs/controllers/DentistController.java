package com.example.patternslabs.controllers;

import com.example.patternslabs.models.Dentist;
import com.example.patternslabs.services.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dentists")
public class DentistController {

    @Autowired
    private DentistService dentistService;

    @GetMapping("/{id}")
    public ResponseEntity<Dentist> getDentist(@PathVariable int id) {
        if (dentistService.hasDentist(id)) {
            return new ResponseEntity<>(dentistService.getDentist(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
