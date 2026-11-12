package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetSpecialtyRepository
        extends JpaRepository<VetSpecialty, VetSpecialtyId> {

    // Fetch the specialities assigned to a vet
    List<VetSpecialty> findByIdVetId(Integer vetId);

    // Fetch the vets that have a speciality
    List<VetSpecialty> findByIdSpecialtyId(Integer specialtyId);

    @Override
    List<VetSpecialty> findAll();
}