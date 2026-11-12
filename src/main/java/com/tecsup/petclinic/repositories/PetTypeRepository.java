package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetTypeRepository
        extends JpaRepository<PetType, Integer> {

    // Fetch pet types by name
    List<PetType> findByName(String name);

    // Fetch pet types by active status
    List<PetType> findByActive(Boolean active);

    // Fetch pet types by care level
    List<PetType> findByCareLevel(String careLevel);

    @Override
    List<PetType> findAll();
}