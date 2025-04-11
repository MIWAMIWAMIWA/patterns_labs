package com.example.patternslabs.csv;

import com.example.patternslabs.models.*;
import com.example.patternslabs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class CsvDataImporter {

    @Autowired private PatientService patientService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private MedicalHistoryService medicalHistoryService;
    @Autowired private DiagnosisService diagnosisService;
    @Autowired private DentistService dentistService;
    @Autowired private PaymentService paymentService;

    public void importCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;

                String entityType = parts[0].trim();
                int id = Integer.parseInt(parts[1].trim());
                String[] data = parts[2].split(",");

                switch (entityType) {
                    case "Dentist" -> {
                        if (!dentistService.hasDentist(id)) {
                            Dentist d = new Dentist(id, data[0], data[1], data[2], data[3]);
                            dentistService.createDentist(d);
                        }
                    }
                    case "Diagnosis" -> {
                        if (!diagnosisService.hasDiagnosis(id)) {
                            Diagnosis diag = new Diagnosis(id,
                                    Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                    data[2], data[3]);
                            diagnosisService.createDiagnosis(diag);
                        }
                    }
                    case "Payment" -> {
                        if (!paymentService.hasPayment(id)) {
                            Payment pay = new Payment(id,
                                    Boolean.parseBoolean(data[0]), data[1]);
                            paymentService.createPayment(pay);
                        }
                    }
                    case "Patient" -> {
                        if (!patientService.hasPatient(id)) {
                            Patient p = new Patient();
                            p.setId(id);
                            p.setName(data[0]);
                            p.setSurname(data[1]);
                            p.setAge(Integer.parseInt(data[2]));
                            List<Integer> appointmentIds = parseIntList(data.length > 3 ? data[3] : "");
                            p.setAppointments(appointmentIds);
                            patientService.createPatient(p);
                        }
                    }
                    case "Appointment" -> {
                        if (!appointmentService.hasAppointment(id)) {
                            Date date = Date.from(Instant.parse(data[5]));
                            Appointment a = new Appointment(id,
                                    Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                    Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                                    date);
                            appointmentService.createAppointment(a);
                        }
                    }
                    case "MedicalHistory" -> {
                        if (!medicalHistoryService.hasMedicalHistory(id)) {
                            MedicalHistory m = new MedicalHistory();
                            m.setId(id);
                            m.setBadTeeth(parseIntList(data[0]));
                            m.setDiagnoses(parseIntList(data[1]));
                            m.setLinkXRay(data[2]);
                            medicalHistoryService.createMedicalHistory(m);
                        }
                    }
                }
            }

            System.out.println("CSV imported successfully.");
        } catch (Exception e) {
            System.err.println("Error importing CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Integer> parseIntList(String raw) {
        if (raw == null || raw.isBlank()) return new ArrayList<>();
        return Arrays.stream(raw.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}


