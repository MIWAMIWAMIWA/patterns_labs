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
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private int age;

    @ElementCollection
    @CollectionTable(name = "patient_appointments", joinColumns = @JoinColumn(name = "patient_id"))
    @Column(name = "appointment_id")
    private List<Integer> appointments;

    private Long medicalHistoryId;
}
