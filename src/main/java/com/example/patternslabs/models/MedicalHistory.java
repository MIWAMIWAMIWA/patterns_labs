package com.example.patternslabs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_histories")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name = "bad_teeth", joinColumns = @JoinColumn(name = "medical_history_id"))
    @Column(name = "tooth_number")
    private List<Integer> badTeeth;

    @ElementCollection
    @CollectionTable(name = "medical_diagnoses", joinColumns = @JoinColumn(name = "medical_history_id"))
    @Column(name = "diagnosis_id")
    private List<Integer> diagnoses;

    private String allergies;

    private String medicalCond;

    private String linkXRay;
}
