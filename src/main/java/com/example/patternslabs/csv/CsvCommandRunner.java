package com.example.patternslabs.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class CsvCommandRunner implements CommandLineRunner {

    @Autowired
    private DataGenerator mockDataGenerator;

    @Autowired
    private CsvDataImporter csvDataImporter;

    private static final String CSV_PATH = "data.csv";

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            System.out.println("No arguments provided. Use 'generate' or 'import'.");
            return;
        }

        switch (args[0]) {
            case "generate" -> {
                System.out.println("Generating mock data...");
                mockDataGenerator.generateMockData(CSV_PATH);
                System.out.println("Data generated to: " + CSV_PATH);
            }
            case "import" -> {
                System.out.println("Importing data from CSV...");
                csvDataImporter.importCsv(CSV_PATH);
                System.out.println("Import completed.");
            }
            default -> System.out.println("Unknown argument: " + args[0] + ". Use 'generate' or 'import'.");
        }
    }
}


