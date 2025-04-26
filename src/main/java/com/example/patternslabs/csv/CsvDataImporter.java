package com.example.patternslabs.csv;

import com.example.patternslabs.models.*;
import com.example.patternslabs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CsvDataImporter {

    @Autowired private PatientService patientService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private MedicalHistoryService medicalHistoryService;
    @Autowired private DiagnosisService diagnosisService;
    @Autowired private DentistService dentistService;
    @Autowired private PaymentService paymentService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public void importCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length < 3) continue;
                String entityType = parts[0];
                int id = Integer.parseInt(parts[1]);
                String[] data = parts[2].split(",");
                Thread.sleep(1);
                switch (entityType) {
                    case "Dentist" -> {
                        Dentist d = new Dentist();
                        d.setName(data[0]);
                        d.setSurname(data[1]);
                        d.setSpecialty(data[2]);
                        d.setExperience(data[3]);
                        dentistService.createDentist(d);
                 }
                    case "Payment" -> {
                        if (!paymentService.hasPayment(id)) {
                            Payment p = new Payment();
                            p.setStatus(Boolean.parseBoolean(data[0]));
                            p.setLinkToPayment(data[1]);
                            paymentService.createPayment(p);
                        }
                    }
                    case "Patient" -> {
                        String[] fields = line.split(",", -1);
                        if (fields.length < 6) {
                            System.err.println("Invalid Patient entry: " + line);
                            break;
                        }

                        Patient p = new Patient();
                        p.setName(fields[2]);
                        p.setSurname(fields[3]);
                        p.setAge(Integer.parseInt(fields[4]));

                        if (!fields[5].isEmpty()) {
                            try {
                                p.setMedicalHistoryId(Long.parseLong(fields[5]));
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid medicalHistoryId for Patient: " + line);
                            }
                        }

                        if (fields.length > 6 && !fields[6].isEmpty()) {
                            p.setAppointments(parseIntList(fields[6]));
                        }

                        patientService.createPatient(p);
                    }


                    case "MedicalHistory" -> {
                        if (data.length < 5) {
                            System.err.println("Invalid MedicalHistory entry: " + Arrays.toString(data));
                            break;
                        }
                        MedicalHistory m = new MedicalHistory();
                        m.setBadTeeth(parseIntList(data[0]));
                        m.setLinkXRay(data[2]);
                        m.setAllergies(data[3]);
                        m.setMedicalCond(data[4]);
                        medicalHistoryService.createMedicalHistory(m);
                    }

                    case "Diagnosis" -> {
                        Diagnosis d = new Diagnosis();
                        d.setPatientId(Integer.parseInt(data[0]));
                        d.setDentistId(Integer.parseInt(data[1]));
                        d.setDiagnosis(data[2]);
                        d.setTreatment(data[3]);
                        diagnosisService.createDiagnosis(d);
                    }
                    case "Appointment" -> {
                        Appointment a = new Appointment();
                        a.setPatientId(Integer.parseInt(data[0]));
                        a.setDentistId(Integer.parseInt(data[1]));
                        a.setDiagnosisId(Integer.parseInt(data[2]));
                        a.setPaymentId(Integer.parseInt(data[3]));
                        a.setDate(dateFormat.parse(data[4]));
                        appointmentService.createAppointment(a);
                    }
                }
            }

            System.out.println("CSV import completed.");
        } catch (Exception e) {
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
