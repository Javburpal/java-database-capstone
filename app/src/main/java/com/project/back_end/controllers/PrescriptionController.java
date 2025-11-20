package com.project.back_end.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.path}/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/{doctorToken}")
    public ResponseEntity<?> savePrescription(
            @RequestBody PrescriptionDto prescription,
            @PathVariable String doctorToken) {

        if (!service.validateToken(doctorToken, "doctor")) {
            return ResponseEntity.status(403).body("Invalid token");
        }

        boolean saved = prescriptionService.savePrescription(prescription);

        if (!saved) {
            return ResponseEntity.badRequest().body("Failed to save prescription");
        }

        appointmentService.updateStatus(prescription.getAppointmentId());

        return ResponseEntity.ok("Prescription saved");
    }

    @GetMapping("/{appointmentId}/{doctorToken}")
    public ResponseEntity<?> getPrescription(
            @PathVariable Long appointmentId,
            @PathVariable String doctorToken) {

        if (!service.validateToken(doctorToken, "doctor")) {
            return ResponseEntity.status(403).body("Invalid token");
        }

        var prescription = prescriptionService.getPrescriptionByAppointmentId(appointmentId);

        if (prescription == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prescription);
    }
}
