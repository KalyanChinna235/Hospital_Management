package com.patient_service.controller;

import com.patient_service.dto.CreatePatientValidationGroup;
import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponseDto;
import com.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "Apis for managing patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping()
    @Operation(summary = "Get All Patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patientResponseDtos = patientService.getAllPatients();
        return ResponseEntity.ok(patientResponseDtos);
    }

    @PostMapping("/create")
    @Operation(summary = "Create Patient")
    public ResponseEntity<PatientResponseDto> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequest patientRequest) {
        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequest);
        return ResponseEntity.ok(patientResponseDto);
    }

    //localhost:4000/patients/12
    @PutMapping("/{id}")
    @Operation(summary = "Update Patient")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable Long id, @Validated(Default.class) @RequestBody PatientRequest patientRequest) {

        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patientRequest);
        return ResponseEntity.ok(patientResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
