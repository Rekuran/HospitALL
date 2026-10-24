package com.hospitall.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospitall.server.model.Patient;
import com.hospitall.server.repository.PatientRepository;
import com.hospitall.shared.PatientDTO;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PatientDTO> getAll() {
        return repository.findAllByOrderByGenderAsc().stream()
                .map(p -> new PatientDTO(p.getPatientId(), p.getName(), p.getAge(), p.getGender(), p.getContact(), 
                        p.getCreatedAt() != null ? p.getCreatedAt().toString() : null, 
                        p.getUpdatedAt() != null ? p.getUpdatedAt().toString() : null))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PatientDTO dto) {
        if (repository.existsByPatientId(dto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        repository.save(new Patient(dto.getId(), dto.getName(), dto.getAge(), dto.getGender(), dto.getContact()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody PatientDTO dto) {
        Patient p = repository.findByPatientId(id).orElse(null);
        if (p == null) return ResponseEntity.notFound().build();
        p.setName(dto.getName());
        p.setAge(dto.getAge());
        p.setGender(dto.getGender());
        p.setContact(dto.getContact());
        repository.save(p);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Patient p = repository.findByPatientId(id).orElse(null);
        if (p == null) return ResponseEntity.notFound().build();
        repository.delete(p);
        return ResponseEntity.ok().build();
    }
}
