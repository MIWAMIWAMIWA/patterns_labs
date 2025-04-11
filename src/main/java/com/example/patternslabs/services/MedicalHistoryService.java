package com.example.patternslabs.services;
import com.example.patternslabs.models.MedicalHistory;
import com.example.patternslabs.repositories.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public void createMedicalHistory(MedicalHistory medicalHistory) {
        medicalHistoryRepository.save(medicalHistory);
    }

    public MedicalHistory getMedicalHistory(int id) {
        return medicalHistoryRepository.getReferenceById(id);
    }

    public boolean hasMedicalHistory(int id) {
        return medicalHistoryRepository.existsById(id);
    }

    public void deleteMedicalHistory(int id) {
        medicalHistoryRepository.deleteById(id);
    }
}
