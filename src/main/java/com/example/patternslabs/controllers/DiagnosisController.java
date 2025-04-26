package com.example.patternslabs.controllers;

import com.example.patternslabs.models.Diagnosis;
import com.example.patternslabs.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diagnoses")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosis(@PathVariable int id) {
        if (diagnosisService.hasDiagnosis(id)) {
            return new ResponseEntity<>(diagnosisService.getDiagnosis(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
