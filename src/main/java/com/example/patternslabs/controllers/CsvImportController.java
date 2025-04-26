package com.example.patternslabs.controllers;

import com.example.patternslabs.csv.CsvDataImporter;
import com.example.patternslabs.csv.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
public class CsvImportController {

    @Autowired
    private CsvDataImporter csvDataImporter;

    @Autowired

    private DataGenerator dataGenerator;

    @PostMapping
    public ResponseEntity<String> importCsv() {
        try {
            String path = "data2.csv";
            csvDataImporter.importCsv(path);
            return ResponseEntity.ok("CSV import successful.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("CSV import failed: " + e.getMessage());
        }
    }

    @GetMapping
    public String generateCsv() {
        String filePath = "data2.csv";
        dataGenerator.generateData(filePath);
        return "CSV data generated.";
    }
}
