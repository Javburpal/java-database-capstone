package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    // Constructor injection for dependencies
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // -----------------------------
    // CREATE: Book Appointment
    // -----------------------------
    @Transactional
    public Appointment bookAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }

        return appointmentRepository.save(appointment);
    }

    // -----------------------------
    // UPDATE Appointment
    // -----------------------------
    @Transactional
    public Appointment updateAppointment(Long appointmentId, Appointment updatedData) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.setDate(updatedData.getDate());
        appointment.setTime(updatedData.getTime());
        appointment.setDoctorId(updatedData.getDoctorId());
        appointment.setPatientId(updatedData.getPatientId());
        appointment.setStatus(updatedData.getStatus());

        return appointmentRepository.save(appointment);
    }

    // -----------------------------
    // DELETE: Cancel Appointment
    // -----------------------------
    @Transactional
    public boolean cancelAppointment(Long appointmentId) {

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isEmpty()) {
            return false; // could not cancel
        }

        appointmentRepository.delete(appointment.get());
        return true;
    }

    // -----------------------------
    // LIST: Get Appointments
    // -----------------------------
    public List<Appointment> getAppointments(Long doctorId, String patientName) {

        if (patientName != null && !patientName.isBlank()) {
            return appointmentRepository.findByDoctorIdAndPatientNameContainingIgnoreCase(
                    doctorId, patientName);
        }

        return appointmentRepository.findByDoctorId(doctorId);
    }

    // -----------------------------
    // CHANGE STATUS
    // -----------------------------
    @Transactional
    public Appointment changeStatus(Long appointmentId, String newStatus) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        appointment.setStatus(newStatus);

        return appointmentRepository.save(appointment);
    }
}
