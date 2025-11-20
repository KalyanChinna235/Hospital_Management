package com.patient_service.service;

import com.patient_service.dto.PatientRequest;
import com.patient_service.dto.PatientResponseDto;
import com.patient_service.entity.Patient;
import com.patient_service.exception.EmailAllReadyExistException;
import com.patient_service.exception.PatientNotFoundException;
import com.patient_service.grpc.BillingServiceGrpcClient;
import com.patient_service.kafka.KafkaProducer;
import com.patient_service.mapper.PatientMapper;
import com.patient_service.respository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private BillingServiceGrpcClient billingServiceGrpcClient;
    @Autowired
    private KafkaProducer kafkaProducer;

    public List<PatientResponseDto> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDto> patientResponseDto = patients.stream().map(PatientMapper::toDto).toList();
        return patientResponseDto;
    }

    public PatientResponseDto createPatient(PatientRequest patientRequest) {
        Optional<Patient> existingPatient = patientRepository.findByEmail(patientRequest.getEmail());

        if (existingPatient.isPresent()) {
            throw new EmailAllReadyExistException("A patient with this email all ready exist " + patientRequest.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequest));
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());
        kafkaProducer.sendEvent(patient);
        return PatientMapper.toDto(patient);
    }

    public PatientResponseDto updatePatient(Long id, PatientRequest patientRequest) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        // Check if the patient with given ID exists
        if (existingPatient.isEmpty()) {
            throw new PatientNotFoundException("Patient with this id is not found " + id);
        }
        // Optional: check if another patient already uses this email
        boolean existingByEmail = patientRepository.existsByEmailAndIdNot(patientRequest.getEmail(), id);
        if (existingByEmail) {
            throw new EmailAllReadyExistException("A patient with this email already exists: " + patientRequest.getEmail());
        }
        Patient patient = existingPatient.get();
        patient.setName(patientRequest.getName());
        patient.setAddress(patientRequest.getAddress());
        patient.setEmail(patientRequest.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequest.getDateOfBirth()));
        Patient updatePatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatePatient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
