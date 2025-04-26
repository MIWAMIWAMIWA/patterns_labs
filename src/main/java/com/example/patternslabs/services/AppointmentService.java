package com.example.patternslabs.services;
import com.example.patternslabs.models.Appointment;
import com.example.patternslabs.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void createAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public Appointment getAppointment(int id) {
        return appointmentRepository.getReferenceById(id);
    }

    public boolean hasAppointment(int id) {
        return appointmentRepository.existsById(id);
    }

    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }
}
