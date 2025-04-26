package com.example.patternslabs.csv;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
    private static final String[] ALLERGIES = {"Pollen", "Dust", "Peanuts", "Latex", "Penicillin", "None"};
    private static final String[] CONDITIONS = {"Diabetes", "Hypertension", "Asthma", "None", "Heart Disease"};

    private static final Random random = new Random();

    public void generateData(String filename) {
        int dentistIdCounter = 1;
        int paymentIdCounter = 1;
        int patientIdCounter = 1;
        int diagnosisIdCounter = 1;
        int appointmentIdCounter = 1;
        int medicalHistoryIdCounter = 1;

        List<Integer> dentistIds = new ArrayList<>();
        List<Integer> diagnosisIds = new ArrayList<>();
        List<Integer> paymentIds = new ArrayList<>();
        Map<Integer, List<Integer>> patientAppointments = new HashMap<>();
        List<String> lines = new ArrayList<>();
        Map<Integer, String> patientLineMap = new HashMap<>();

        try {
            lines.add("Class,ID,Data");


            for (int i = 0; i < 100; i++) {
                int id = dentistIdCounter++;
                dentistIds.add(id);
                lines.add(String.format("Dentist,%d,%s,%s,%s,%s", id,
                        getRandom(NAMES), getRandom(SURNAMES),
                        getRandom(SPECIALTIES), getRandom(EXPERIENCES)));
            }


            for (int i = 0; i < 200; i++) {
                int id = paymentIdCounter++;
                paymentIds.add(id);
                lines.add(String.format("Payment,%d,%s,%s", id,
                        random.nextBoolean(), getRandom(LINKS)));
            }


            for (int i = 0; i < 300; i++) {
                int patientId = patientIdCounter++;
                int medicalHistoryId = medicalHistoryIdCounter++;


                List<Integer> badTeeth = getRandomIntList(3, 5, 1, 32);
                List<Integer> diagList = getRandomSublist(diagnosisIds, 3, 5);
                String allergy = getRandom(ALLERGIES);
                String condition = getRandom(CONDITIONS);

                lines.add(String.format("MedicalHistory,%d,%s,%s,%s,%s,%s", medicalHistoryId,
                        toCsvArray(badTeeth), toCsvArray(diagList),
                        getRandom(LINKS), allergy, condition));


                patientLineMap.put(patientId, String.format("Patient,%d,%s,%s,%d,%d,",
                        patientId, getRandom(NAMES), getRandom(SURNAMES),
                        random.nextInt(40) + 10, medicalHistoryId));
            }


            List<Integer> patientIds = new ArrayList<>(patientLineMap.keySet());
            for (int i = 0; i < 200; i++) {
                int id = diagnosisIdCounter++;
                diagnosisIds.add(id);
                int patientId = getRandomFromList(patientIds);
                int dentistId = getRandomFromList(dentistIds);

                lines.add(String.format("Diagnosis,%d,%d,%d,%s,%s", id,
                        patientId, dentistId,
                        getRandom(DIAGNOSES), getRandom(TREATMENTS)));
            }


            for (Integer patientId : patientIds) {
                List<Integer> appointments = new ArrayList<>();
                int apptCount = 3 + random.nextInt(3); // 3-5

                for (int j = 0; j < apptCount; j++) {
                    int appointmentId = appointmentIdCounter++;
                    appointments.add(appointmentId);
                    int dentistId = getRandomFromList(dentistIds);
                    int paymentId = getRandomFromList(paymentIds);
                    int diagnosisId = getRandomFromList(diagnosisIds);
                    String date = formatDate(new Date());

                    lines.add(String.format("Appointment,%d,%d,%d,%d,%d,%s", appointmentId,
                            patientId, dentistId, diagnosisId, paymentId, date));
                }

                patientAppointments.put(patientId, appointments);
            }


            for (Map.Entry<Integer, String> entry : patientLineMap.entrySet()) {
                int pid = entry.getKey();
                String base = entry.getValue();
                String apptList = toCsvArray(patientAppointments.get(pid));
                lines.add(base + apptList);
            }

            Files.write(Paths.get(filename), lines);
            System.out.println("CSV generated at: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRandom(String[] arr) {
        return arr[random.nextInt(arr.length)];
    }

    private static int getRandomFromList(List<Integer> list) {
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

    private static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmX");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}


