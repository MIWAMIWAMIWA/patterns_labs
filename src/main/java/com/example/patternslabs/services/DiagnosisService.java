package com.example.patternslabs.services;

import com.example.patternslabs.models.Diagnosis;
import com.example.patternslabs.repositories.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public void createDiagnosis(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }

    public Diagnosis getDiagnosis(int id) {
        return diagnosisRepository.getReferenceById(id);
    }

    public boolean hasDiagnosis(int id) {
        return diagnosisRepository.existsById(id);
    }

    public void deleteDiagnosis(int id) {
        diagnosisRepository.deleteById(id);
    }
}

