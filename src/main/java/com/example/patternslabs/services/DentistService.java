package com.example.patternslabs.services;


import com.example.patternslabs.models.Dentist;
import com.example.patternslabs.repositories.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistService {

    @Autowired
    private DentistRepository dentistRepository;

    public void createDentist(Dentist dentist) {
        dentistRepository.save(dentist);
    }

    public Dentist getDentist(int id) {
        return dentistRepository.getReferenceById(id);
    }

    public boolean hasDentist(int id) {
        return dentistRepository.existsById(id);
    }

    public void deleteDentist(int id) {
        dentistRepository.deleteById(id);
    }

    public void updateDentist(Dentist dentist) {
        dentistRepository.save(dentist);
    }

    public List<Dentist> getAllDentists() {
        return dentistRepository.findAll();
    }


}
