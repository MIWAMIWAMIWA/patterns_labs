package com.example.patternslabs.csv;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataGenerator {

    private static final String[] NAMES = {"Alex", "Taylor", "Jordan", "Morgan"};
    private static final String[] SURNAMES = {"Smith", "Brown", "Johnson", "Lee"};
    private static final String[] SPECIALTIES = {"Orthodontist", "Endodontist", "Periodontist", "Prosthodontist"};
    private static final String[] EXPERIENCES = {"2 years", "5 years", "10 years", "15 years"};
    private static final String[] DIAGNOSES = {"Cavity", "Gum Disease", "Tooth Decay", "Plaque Buildup"};
    private static final String[] TREATMENTS = {"Filling", "Cleaning", "Extraction", "Root Canal"};
    private static final String[] LINKS = {"http://xray1.com", "http://xray2.com", "http://xray3.com", "http://xray4.com"};

    private static final Random random = new Random();

    public void generateMockData(String filename) {
        int idCounter = 1;
        List<Integer> dentistIds = new ArrayList<>();
        List<Integer> diagnosisIds = new ArrayList<>();
        List<Integer> paymentIds = new ArrayList<>();
        List<Integer> patientIds = new ArrayList<>();
        Map<Integer, List<Integer>> patientAppointments = new HashMap<>();

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Class,ID,Data\n");

            // Dentists
            for (int i = 0; i < 100; i++) {
                int id = idCounter++;
                dentistIds.add(id);
                writer.write(String.format("Dentist,%d,%s,%s,%s,%s\n", id,
                        getRandom(NAMES), getRandom(SURNAMES),
                        getRandom(SPECIALTIES), getRandom(EXPERIENCES)));
            }

            // Payments
            for (int i = 0; i < 200; i++) {
                int id = idCounter++;
                paymentIds.add(id);
                writer.write(String.format("Payment,%d,%s,%s\n", id,
                        random.nextBoolean(), getRandom(LINKS)));
            }

            // Patients & Appointments
            for (int i = 0; i < 300; i++) {
                int patientId = idCounter++;
                patientIds.add(patientId);
                List<Integer> appointments = new ArrayList<>();

                writer.write(String.format("Patient,%d,%s,%s,%d,\n", patientId,
                        getRandom(NAMES), getRandom(SURNAMES), random.nextInt(40) + 10));

                int apptCount = 3 + random.nextInt(3); // 3-5
                for (int j = 0; j < apptCount; j++) {
                    int appointmentId = idCounter++;
                    appointments.add(appointmentId);
                    int dentistId = getRandomFromList(dentistIds, false);
                    int paymentId = getRandomFromList(paymentIds, false);

                    writer.write(String.format("Appointment,%d,%d,%d,0,%d,%s\n", appointmentId,
                            patientId, dentistId, paymentId, new Date().toInstant().toString()));
                }

                patientAppointments.put(patientId, appointments);
            }

            // Diagnoses
            for (int i = 0; i < 200; i++) {
                int id = idCounter++;
                diagnosisIds.add(id);
                int patientId = getRandomFromList(patientIds, false);
                int dentistId = getRandomFromList(dentistIds, false);

                writer.write(String.format("Diagnosis,%d,%d,%d,%s,%s\n", id,
                        patientId, dentistId,
                        getRandom(DIAGNOSES), getRandom(TREATMENTS)));
            }

            // Medical History
            for (int i = 0; i < 100; i++) {
                int id = idCounter++;
                List<Integer> badTeeth = getRandomIntList(3, 5, 1, 32);
                List<Integer> diagList = getRandomSublist(diagnosisIds, 3, 5);

                writer.write(String.format("MedicalHistory,%d,%s,%s,%s\n", id,
                        toCsvArray(badTeeth), toCsvArray(diagList), getRandom(LINKS)));
            }

            // Append Appointments to Patient lines (retroactively)
            List<String> lines = Files.readAllLines(Paths.get(filename));
            try (FileWriter finalWriter = new FileWriter(filename)) {
                for (String line : lines) {
                    if (line.startsWith("Patient")) {
                        String[] parts = line.split(",", 5);
                        int patientId = Integer.parseInt(parts[1]);
                        String apptList = toCsvArray(patientAppointments.get(patientId));
                        line = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4] + apptList;
                    }
                    finalWriter.write(line + "\n");
                }
            }

            System.out.println("CSV generated at: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandom(String[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    private static int getRandomFromList(List<Integer> list, boolean allowZero) {
        if (list.isEmpty()) return allowZero ? 0 : 1;
        return list.get(random.nextInt(list.size()));
    }

    private static List<Integer> getRandomIntList(int minCount, int maxCount, int minVal, int maxVal) {
        int count = minCount + random.nextInt(maxCount - minCount + 1);
        Set<Integer> set = new HashSet<>();
        while (set.size() < count) {
            set.add(minVal + random.nextInt(maxVal - minVal + 1));
        }
        return new ArrayList<>(set);
    }

    private static List<Integer> getRandomSublist(List<Integer> list, int min, int max) {
        List<Integer> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled);
        int count = Math.min(shuffled.size(), min + random.nextInt(max - min + 1));
        return shuffled.subList(0, count);
    }

    private static String toCsvArray(List<Integer> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining("|"));
    }
}

