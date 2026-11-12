package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository
        extends JpaRepository<Visit, Integer> {

    // Fetch visits by pet
    List<Visit> findByPetId(Integer petId);

    // Fetch visits by vet
    List<Visit> findByVetId(Integer vetId);

    // Fetch visits by date
    List<Visit> findByVisitDate(LocalDate visitDate);

    @Override
    List<Visit> findAll();
}