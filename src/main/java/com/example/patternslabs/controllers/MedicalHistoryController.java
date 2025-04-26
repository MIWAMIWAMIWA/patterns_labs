package com.example.patternslabs.controllers;

import com.example.patternslabs.models.MedicalHistory;
import com.example.patternslabs.services.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-histories")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistory> getMedicalHistory(@PathVariable int id) {
        if (medicalHistoryService.hasMedicalHistory(id)) {
            return new ResponseEntity<>(medicalHistoryService.getMedicalHistory(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
