package com.example.patternslabs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int patientId;

    private int dentistId;

    private int diagnosisId;

    private int paymentId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
