package com.patient_service.mapper;

import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponseDto;
import com.patient_service.entity.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDto toDto(Patient patient) {
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        patientResponseDto.setId(patient.getId());
        patientResponseDto.setName(patient.getName());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setAddress(patient.getAddress());
        patientResponseDto.setDateOfBirth(patient.getDateOfBirth());
        return patientResponseDto;
    }

    public static Patient toEntity(PatientRequest patientRequest) {
        Patient patient = new Patient();
        patient.setName(patientRequest.getName());
        patient.setEmail(patientRequest.getEmail());
        patient.setAddress(patientRequest.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequest.getRegisterdDate()));
        return patient;
    }
}
